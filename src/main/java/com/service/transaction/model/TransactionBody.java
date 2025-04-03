package com.service.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBody {
    private Integer categoryId;
    private double amount;
    private PaymentType paymentType;
    private Date date;
    private String note;
    private TypeOfTransaction typeOfTransaction;
}
