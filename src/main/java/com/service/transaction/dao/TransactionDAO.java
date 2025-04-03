package com.service.transaction.dao;

import com.service.transaction.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TransactionDAO extends ReactiveCrudRepository<Transaction, Integer> {
    Flux<Transaction> findByUserId(String userId);
}
