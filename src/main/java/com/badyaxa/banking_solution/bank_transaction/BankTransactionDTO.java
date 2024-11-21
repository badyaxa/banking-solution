package com.badyaxa.banking_solution.bank_transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
public class BankTransactionDTO {

    private UUID id;

    @NotNull
    private BigDecimal value;

    @NotNull
    private TransactionType operation;

    @Size(min = 29, max = 29, message = "IBAN must be 29 characters long")
    private String bankAccount;
}
