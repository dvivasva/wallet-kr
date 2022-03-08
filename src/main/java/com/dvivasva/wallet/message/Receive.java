package com.dvivasva.wallet.message;

import com.dvivasva.wallet.dto.WalletDto;
import com.dvivasva.wallet.entity.Payment;
import com.dvivasva.wallet.service.WalletService;
import com.dvivasva.wallet.utils.JsonUtils;
import com.dvivasva.wallet.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class Receive {

    private static final Logger logger = LoggerFactory.getLogger(Receive.class);
    private final WalletService walletService;
    private final Sender sender;

    @KafkaListener(topics = Topic.INS_PAYMENT, groupId = "group_id_wallet")
    public void consumeGateway(String param) {
        logger.info("Has been published an insert payment from service gateway-mobile : " + param);
        sendMessageToCard(param);
    }

    public void sendMessageToCard(String param) {
        Payment payment;
        try {
            payment = JsonUtils.convertFromJsonToObject(param, Payment.class);
            Mono<WalletDto> walletDtoOrigin = walletService.findByNumberPhone(payment.getNumberPhoneOrigin());
            walletDtoOrigin
                    .switchIfEmpty(Mono.error(new ClassNotFoundException("not exist wallet")))
                    .doOnNext(p -> {
                        payment.setNumberPhoneOrigin(p.getNumberCard());
                        Mono<WalletDto> walletDtoDestination = walletService.findByNumberPhone(payment.getNumberPhoneDestination());
                        walletDtoDestination.switchIfEmpty(Mono.error(new ClassNotFoundException("not exist wallet")))
                                .doOnNext(v -> {
                                    payment.setNumberPhoneDestination(v.getNumberCard());
                                     sender.sendRequestPaymentToCard(payment);
                                    logger.info("send messages to card-rk -->");
                                }).subscribe();
                    }).subscribe();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @KafkaListener(topics = Topic.RESPONSE_PAYMENT_ON_WALLET, groupId = "group_id_wallet")
    public void consumeCard(String param) {
        logger.info("Has been published an insert payment from service card-rk : " + param);
        sendMessageToPayment(param);
    }

    public void sendMessageToPayment(String param) {
        Payment payment;
        try {
            payment = JsonUtils.convertFromJsonToObject(param, Payment.class);
            Mono<WalletDto> walletDtoOrigin = walletService.findByNumberCard(payment.getNumberPhoneOrigin());
            walletDtoOrigin
                    .switchIfEmpty(Mono.error(new ClassNotFoundException("not exist wallet")))
                    .doOnNext(p -> {
                        payment.setNumberPhoneOrigin(p.getNumberPhone());
                        Mono<WalletDto> walletDtoDestination = walletService.findByNumberCard(payment.getNumberPhoneDestination());
                        walletDtoDestination.switchIfEmpty(Mono.error(new ClassNotFoundException("not exist wallet")))
                                .doOnNext(v -> {
                                    payment.setNumberPhoneDestination(v.getNumberPhone());
                                    sender.sendResponsePaymentToPayment(payment);
                                    logger.info("send messages to payment-rk -->");
                                }).subscribe();
                    }).subscribe();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
