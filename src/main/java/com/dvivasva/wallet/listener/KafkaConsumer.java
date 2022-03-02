package com.dvivasva.wallet.listener;


import com.dvivasva.wallet.component.WalletComponent;
import com.dvivasva.wallet.entity.Wallet;
import com.dvivasva.wallet.service.KafkaProducer;
import com.dvivasva.wallet.util.JsonUtils;
import com.dvivasva.wallet.util.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final WalletComponent walletComponent;
    private final KafkaProducer kafkaProducer;

    /**
     * create new wallet
     * @param param .
     */
    @KafkaListener(topics = Topic.INS_WALLET, groupId = "group_id")
    public void consume(String param) {
        logger.info("Has been published an insert payment from service gateway-mobile : " + param);
        createWallet(param);
    }

    public void createWallet(String param) {
        var wallet = new Wallet();
        try {
            wallet = JsonUtils.convertFromJsonToObject(param, Wallet.class);
            var ins = walletComponent.create(wallet);
            ins.doOnNext(p -> logger.info("registry success" + p))
                    .subscribe();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    @KafkaListener(topics = "ins-wallet-json", groupId = "group_json",
            containerFactory = "walletKafkaListenerFactory")
    public void consumeJson(Wallet wallet) {
        logger.info("Consumed JSON Message: " + wallet);
    }*/


    //================================
    @KafkaListener(topics = Topic.FIND_CELL_ORIGIN, groupId = "group_id")
    public void consumeCellOrigin(String param) {
        logger.info("Has been published find number cell origin from service payment-kr : " + param);
        sendMessageCard(param, 0);

    }

    @KafkaListener(topics = Topic.FIND_CELL_DESTINATION, groupId = "group_id")
    public void consumeCellDestination(String param) {
        logger.info("Has been published find number cell destination from service payment-kr : " + param);
        sendMessageCard(param, 1);

    }

    public void sendMessageCard(String param, int index) {
        String newPhone = JsonUtils.removeFirstAndLast(param);
        var find = walletComponent.findByNumberPhone(newPhone);
        find.doOnNext(p -> {
            if (index == 0) {
                kafkaProducer.sendNumberCardOriginToCard(p.getNumberCard());
            } else {
                kafkaProducer.sendNumberCardDestinationToCard(p.getNumberCard());
            }
            logger.info("send messages to card -->");
        }).subscribe();
    }



}
