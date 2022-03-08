package com.dvivasva.wallet.message;

import com.dvivasva.wallet.entity.Payment;
import com.dvivasva.wallet.utils.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Sender {

    private static final Logger logger = LoggerFactory.getLogger(Sender.class);
    private final KafkaTemplate<String, Payment> requestPaymentKafkaTemplate;

    public void sendRequestPaymentToCard(Payment value) {
        requestPaymentKafkaTemplate.send(Topic.FIND_NUMBERS_CARDS,value);
        logger.info("Messages successfully pushed on topic: " + Topic.FIND_NUMBERS_CARDS);
    }

    public void sendResponsePaymentToPayment(Payment payment) {
        requestPaymentKafkaTemplate.send(Topic.RESPONSE_PAYMENT_ON_PAYMENT,payment);
        logger.info("Messages successfully pushed on topic: " + Topic.RESPONSE_PAYMENT_ON_PAYMENT);
    }
}
