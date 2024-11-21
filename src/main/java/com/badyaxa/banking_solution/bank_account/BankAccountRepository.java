package com.badyaxa.banking_solution.bank_account;

import com.badyaxa.banking_solution.account_owner.AccountOwner;
import com.badyaxa.banking_solution.bank_card.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    BankAccount findFirstByAccountOwner(AccountOwner accountOwner);

    BankAccount findFirstByBankCard(BankCard bankCard);

    boolean existsByIbanIgnoreCase(String iban);

    boolean existsByBankCardCardNumberIgnoreCase(String cardNumber);

}
