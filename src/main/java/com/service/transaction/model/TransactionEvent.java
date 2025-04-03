package com.service.transaction.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionEvent {
    private Transaction transaction;
    private String action;

    public TransactionEvent(Transaction transaction, String action) {
        this.transaction = transaction;
        this.action = action;
    }
}

