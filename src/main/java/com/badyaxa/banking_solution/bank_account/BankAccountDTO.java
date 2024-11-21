package com.badyaxa.banking_solution.bank_account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class BankAccountDTO {

    @Size(max = 29)
    @BankAccountIbanValid
    private String iban;

    private BigDecimal balance;

    @NotNull
    private Currency currency;

    @NotNull
//    @Size(max = 12)
    @Pattern(regexp = "\\d{10}|\\d{12}", message = "The individual tax number must contain only digits and be exactly 10 or 12 characters long.")
    private String accountOwner;

    @Size(min = 16, max = 16, message = "The bank card number must be exactly 16 characters long.")
    @BankAccountBankCardUnique
    private String bankCard;

}
