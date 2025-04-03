package com.service.transaction.model;

import java.util.Date;

public class TransactionBody {
    private Integer categoryId;
    private double amount;
    private PaymentType paymentType;
    private Date date;
    private String note;
    private TypeOfTransaction typeOfTransaction;
}
