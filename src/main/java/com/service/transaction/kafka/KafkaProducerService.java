package com.service.transaction.kafka;

import com.service.transaction.model.Transaction;
import com.service.transaction.model.TransactionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaProducerService {

    private static final String TRANSACTIONS_TOPIC = "transactions";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendTransactionEvent(Transaction transaction, String action) {
        try {
            TransactionEvent transactionEvent = new TransactionEvent(transaction, action);
            String transactionJson = objectMapper.writeValueAsString(transactionEvent);
            kafkaTemplate.send(TRANSACTIONS_TOPIC, transactionJson);
            System.out.println("✅ Sent transaction to MSK: " + transactionJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

