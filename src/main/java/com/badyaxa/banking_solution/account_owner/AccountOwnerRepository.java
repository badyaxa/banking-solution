package com.badyaxa.banking_solution.account_owner;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountOwnerRepository extends JpaRepository<AccountOwner, String> {

    boolean existsByIndividualTaxNumberIgnoreCase(String individualTaxNumber);

}
