package com.dvivasva.wallet.service;

import com.dvivasva.wallet.dto.CardDto;
import com.dvivasva.wallet.util.Topic;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;


    public void sendNumberCardOriginToCard(String value) {
        kafkaTemplate.send(Topic.FIND_NUMBER_CARD_ORIGIN,value);
        logger.info("Messages successfully pushed on topic: " + Topic.FIND_NUMBER_CARD_ORIGIN);
    }
    public void sendNumberCardDestinationToCard(String value) {
        kafkaTemplate.send(Topic.FIND_NUMBER_CARD_DESTINATION,value);
        logger.info("Messages successfully pushed on topic: " + Topic.FIND_NUMBER_CARD_DESTINATION);
    }
    /*
    public void sendMessageNumberCard(String value) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(Topic.FIND_NUMBER_CARD, value);
        future.addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                logger.info("Messages failed to push on topic: " + Topic.FIND_NUMBER_CARD);
            }

            @Override
            public void onSuccess(Object result) {
                logger.info("Messages successfully pushed on topic: " + Topic.FIND_NUMBER_CARD);
            }
        });
    }*/


}
