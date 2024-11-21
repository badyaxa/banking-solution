package com.badyaxa.banking_solution.bank_transaction;

public enum TransactionType {
    DEPOSIT("+"),
    WITHDRAW("-");
    private final String name;

    TransactionType(String name) {
        this.name = name;
    }
}
