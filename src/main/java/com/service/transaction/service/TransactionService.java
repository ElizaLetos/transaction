package com.service.transaction.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.transaction.dao.TransactionDAO;
import com.service.transaction.kafka.KafkaProducerService;
import com.service.transaction.model.Transaction;
import com.service.transaction.model.TypeOfTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionDAO repository;

    private final KafkaProducerService kafkaProducerService;

    private final ObjectMapper objectMapper;

    public TransactionService(TransactionDAO repository, KafkaProducerService kafkaProducerService, ObjectMapper objectMapper) {
        this.repository = repository;
        this.kafkaProducerService = kafkaProducerService;
        this.objectMapper = objectMapper;
    }

    public Flux<Transaction> getAllTransactions(String userId) {
        return repository.findByUserId(userId);
    }

    public Flux<Transaction> getTransactionsByType(TypeOfTransaction typeOfTransaction, String userId) {
        return repository.findByUserId(userId)
                .filter(transaction -> transaction.getTypeOfTransaction().equals(typeOfTransaction));
    }

    public Mono<Transaction> getTransactionById(int id, String userId) {
        return repository.findById(id)
                .filter(cat -> cat.getUserId().equals(userId));
    }

    public Mono<Transaction> createTransaction(Transaction transaction) {
        logger.info("Creating transaction: {}", transaction);
        return repository.save(transaction)
                .doOnSuccess(savedTransaction ->
                        kafkaProducerService.sendTransactionEvent(savedTransaction, "created"));
    }

    public Mono<Transaction> updateTransaction(int id, Transaction updatedTransaction, String userId) {
        return repository.findById(id)
                .filter(cat -> cat.getUserId().equals(userId))
                .flatMap(existing -> {
                    existing.setUserId(userId);
                    existing.setAmount(updatedTransaction.getAmount());
                    existing.setPaymentType(updatedTransaction.getPaymentType());
                    existing.setDate(updatedTransaction.getDate());
                    existing.setNote(updatedTransaction.getNote());
                    existing.setTypeOfTransaction(updatedTransaction.getTypeOfTransaction());
                    existing.setCategoryId(updatedTransaction.getCategoryId());
                    return repository.save(existing)
                            .doOnSuccess(savedTransaction ->
                                    kafkaProducerService.sendTransactionEvent(savedTransaction, "updated"));
                });
    }

    public Mono<Void> deleteTransaction(int id, String userId) {
        return repository.findById(id)
                .filter(cat -> cat.getUserId().equals(userId))
                .flatMap(existingTransaction ->
                repository.delete(existingTransaction)
                .doOnSuccess(unused -> kafkaProducerService.sendTransactionEvent(existingTransaction, "deleted"))
                );
    }

    public Mono<Double> sumOfTransactions(TypeOfTransaction typeOfTransaction, String userId) {
        return repository
                .findByUserId(userId)
                .filter(transaction -> transaction.getTypeOfTransaction().equals(typeOfTransaction))
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }
}
