package com.service.transaction.kafka;

import com.service.transaction.model.TransactionEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerService {

    private final ObjectMapper objectMapper;

    public KafkaConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "transactions", groupId = "transaction-group")
    public void consumeTransactionEvent(String message) {
        try {
            TransactionEvent event = objectMapper.readValue(message, TransactionEvent.class);

            if ("deleted".equals(event.getAction())) {
                System.out.println("Transaction deleted: " + event.getTransaction().getId());
            } else {
                System.out.println("Transaction event received: " + event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
