package com.service.transaction.rest;

import com.service.transaction.model.Transaction;
import com.service.transaction.model.TypeOfTransaction;
import com.service.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public Mono<ResponseEntity<List<Transaction>>> getAllTransactions(@RequestParam(required = false) TypeOfTransaction typeOfTransaction, @AuthenticationPrincipal Jwt jwt) {
        if (typeOfTransaction != null) {
            String userId = jwt.getSubject();
            logger.info("UserID{}", userId);
            return service.getTransactionsByType(typeOfTransaction, userId)
                    .collectList()
                    .map(ResponseEntity::ok);
        }
        else {
            String userId = jwt.getSubject();
            return service.getAllTransactions(userId)
                    .collectList()
                    .map(ResponseEntity::ok);
        }
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Transaction>> getById(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
        return service.getTransactionById(id, jwt.getSubject())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/total")
    public Mono<ResponseEntity<Double>> getTotalFromTransactions(@RequestParam TypeOfTransaction typeOfTransaction, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return service.sumOfTransactions(typeOfTransaction, userId)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<Transaction> create(@RequestBody Transaction transaction, @AuthenticationPrincipal Jwt jwt) {
        logger.info("postATransactions");
        transaction.setUserId(jwt.getSubject());
        return service.createTransaction(transaction);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Transaction>> update(@PathVariable int id,
                                                 @RequestBody Transaction transaction,
                                                 @AuthenticationPrincipal Jwt jwt) {
        return service.updateTransaction(id, transaction, jwt.getSubject())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable int id, @AuthenticationPrincipal Jwt jwt) {
        return service.deleteTransaction(id, jwt.getSubject())
                .then(Mono.just(ResponseEntity.noContent().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/report/monthly/expenses")
    public Mono<ResponseEntity<List<Map<String, Object>>>> getMonthlyExpenses(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return service.getMonthlyTransactionReport(TypeOfTransaction.EXPENSE, userId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/report/monthly/incomes")
    public Mono<ResponseEntity<List<Map<String, Object>>>> getMonthlyIncomes(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return service.getMonthlyTransactionReport(TypeOfTransaction.INCOME, userId)
                .map(ResponseEntity::ok);
    }
}
