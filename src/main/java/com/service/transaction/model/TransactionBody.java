package com.service.transaction.model;

import java.util.Date;

public class TransactionBody {
    private Integer categoryId;
    private double amount;
    private PaymentType paymentType;
    private Date date;
    private String note;
    private TypeOfTransaction typeOfTransaction;

    public TransactionBody() {
    }

    public TransactionBody(Integer categoryId, double amount, PaymentType paymentType, Date date, String note, TypeOfTransaction typeOfTransaction) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.date = date;
        this.note = note;
        this.typeOfTransaction = typeOfTransaction;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TypeOfTransaction getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(TypeOfTransaction typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }
}
