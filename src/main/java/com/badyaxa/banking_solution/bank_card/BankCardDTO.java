package com.badyaxa.banking_solution.bank_card;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankCardDTO {

    @Size(max = 20)
    @BankCardCardNumberValid
    private String cardNumber;

    @NotNull
    private Integer expirationMonth;

    @NotNull
    private Integer expirationYear;

    @NotNull
    private Integer cvv;

    @NotNull
    private PaymentSystem paymentSystem;

    @NotNull
    private BankCardType cardType;
}
