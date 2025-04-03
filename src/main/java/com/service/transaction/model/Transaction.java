package com.service.transaction.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Table("transaction")
public class Transaction {
    @Id
    private Integer id;

    private String userId;
    private Integer categoryId;
    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PaymentType paymentType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String note;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TypeOfTransaction typeOfTransaction;

    public Transaction() {
    }

    public Transaction(String userId, Integer categoryId, double amount, LocalDate date, PaymentType paymentType, String note, TypeOfTransaction typeOfTransaction) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
        this.paymentType = paymentType;
        this.note = note;
        this.typeOfTransaction = typeOfTransaction;
    }

    public Transaction(Integer id, String userId, Integer categoryId, double amount, PaymentType paymentType, LocalDate date, String note, TypeOfTransaction typeOfTransaction) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.date = date;
        this.note = note;
        this.typeOfTransaction = typeOfTransaction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
