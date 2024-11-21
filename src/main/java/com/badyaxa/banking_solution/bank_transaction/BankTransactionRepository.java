package com.badyaxa.banking_solution.bank_transaction;

import com.badyaxa.banking_solution.bank_account.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, UUID> {

    BankTransaction findFirstByBankAccount(BankAccount bankAccount);
}
