package com.badyaxa.banking_solution.bank_card;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BankCardRepository extends JpaRepository<BankCard, String> {

    boolean existsByCardNumberIgnoreCase(String cardNumber);

}
