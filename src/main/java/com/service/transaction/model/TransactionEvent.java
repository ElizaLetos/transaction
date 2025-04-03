package com.service.transaction.model;

public class TransactionEvent {
    private Transaction transaction;
    private String action;

    public TransactionEvent() {
    }

    public TransactionEvent(Transaction transaction, String action) {
        this.transaction = transaction;
        this.action = action;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

