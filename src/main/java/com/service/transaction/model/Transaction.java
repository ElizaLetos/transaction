package com.service.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@NoArgsConstructor
@AllArgsConstructor
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
}
