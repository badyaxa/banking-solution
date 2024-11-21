package com.badyaxa.banking_solution.util;

import com.badyaxa.banking_solution.account_owner.AccountOwner;
import com.badyaxa.banking_solution.account_owner.AccountOwnerRepository;
import com.badyaxa.banking_solution.bank_account.BankAccount;
import com.badyaxa.banking_solution.bank_account.BankAccountRepository;
import com.badyaxa.banking_solution.bank_account.Currency;
import com.badyaxa.banking_solution.bank_card.BankCard;
import com.badyaxa.banking_solution.bank_card.BankCardRepository;
import com.badyaxa.banking_solution.bank_card.BankCardType;
import com.badyaxa.banking_solution.bank_card.PaymentSystem;
import com.badyaxa.banking_solution.bank_transaction.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

import static com.badyaxa.banking_solution.util.UtilityMethods.getRandomCode;

@Slf4j
@Configuration
public class DataInit implements ApplicationRunner {
    private final AccountOwnerRepository ownerRepository;
    private final BankAccountRepository accountRepository;
    private final BankCardRepository cardRepository;
    private final TransactionService transactionService;

    public DataInit(AccountOwnerRepository ownerRepository,
                    BankAccountRepository accountRepository,
                    BankCardRepository cardRepository,
                    TransactionService transactionService) {
        this.ownerRepository = ownerRepository;
        this.accountRepository = accountRepository;
        this.cardRepository = cardRepository;
        this.transactionService = transactionService;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("============== running DataInit to init some data");
        AccountOwner owner1 = ownerRepository
                .save(new AccountOwner(getRandomCode(12), "John", "Doe"));
        AccountOwner owner2 = ownerRepository
                .save(new AccountOwner(getRandomCode(12), "Bob", "Gates"));
        AccountOwner owner3 = ownerRepository
                .save(new AccountOwner(getRandomCode(12), "Alice", "Trump"));

        BankAccount account1 = accountRepository.save(new BankAccount(owner1, Currency.UAH));
        BankAccount account1USD = accountRepository.save(new BankAccount(owner1, Currency.USD));
        BankAccount account1EUR = accountRepository.save(new BankAccount(owner1, Currency.EUR));
        BankAccount account2 = accountRepository.save(new BankAccount(owner2, Currency.UAH));
        BankAccount account2EUR = accountRepository.save(new BankAccount(owner2, Currency.EUR));
        BankAccount account3 = accountRepository.save(new BankAccount(owner3, Currency.UAH));
        BankAccount account3U = accountRepository.save(new BankAccount(owner3, Currency.USD));

        BankCard card1 = cardRepository.save(new BankCard(account1));
        BankCard card2 = cardRepository
                .save(new BankCard(PaymentSystem.VISA, BankCardType.DEBIT, account2EUR));

        //Deposit funds into an account.
        transactionService.deposit(account1.getIban(), BigDecimal.valueOf(1234.56));
        //Withdraw funds from an account.
        transactionService.withdraw(account1.getIban(), BigDecimal.valueOf(543.21));
        //Transfer funds between two accounts
        transactionService.transfer(account1.getIban(), account2.getIban(), BigDecimal.valueOf(133.33));
        transactionService.transfer(account2.getIban(), account3.getIban(), BigDecimal.valueOf(11.11));
    }
}
