package com.badyaxa.banking_solution.account_owner;

import com.badyaxa.banking_solution.bank_account.BankAccount;
import com.badyaxa.banking_solution.bank_account.BankAccountRepository;
import com.badyaxa.banking_solution.util.NotFoundException;
import com.badyaxa.banking_solution.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountOwnerService {
    private final AccountOwnerRepository accountOwnerRepository;
    private final BankAccountRepository bankAccountRepository;

    public AccountOwnerService(final AccountOwnerRepository accountOwnerRepository,
                               final BankAccountRepository bankAccountRepository) {
        this.accountOwnerRepository = accountOwnerRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<AccountOwnerDTO> findAll() {
        final List<AccountOwner> accountOwners = accountOwnerRepository.findAll(Sort.by("individualTaxNumber"));
        return accountOwners.stream()
                .map(accountOwner -> mapToDTO(accountOwner, new AccountOwnerDTO()))
                .toList();
    }

    public AccountOwnerDTO get(final String individualTaxNumber) {
//        validate(individualTaxNumber);
        return mapToDTO(getAccountOwner(individualTaxNumber), new AccountOwnerDTO());
    }

    public String create(/*@Valid*/ final AccountOwnerDTO accountOwnerDTO) {
        getAccountOwnerOptional(accountOwnerDTO.getIndividualTaxNumber()).ifPresent(ao -> {
            throw new IllegalArgumentException("Account with individualTaxNumber = '" + ao.getIndividualTaxNumber() + "' already exists");
        });
        final AccountOwner accountOwner = new AccountOwner();
        accountOwner.setIndividualTaxNumber(accountOwnerDTO.getIndividualTaxNumber());
        mapToEntity(accountOwnerDTO, accountOwner);
        return accountOwnerRepository.save(accountOwner).getIndividualTaxNumber();
    }

    public void update(final String individualTaxNumber, /*@Valid*/ final AccountOwnerDTO accountOwnerDTO) {
//        validate(individualTaxNumber);
        final AccountOwner accountOwner = getAccountOwner(individualTaxNumber);
        mapToEntity(accountOwnerDTO, accountOwner);
        accountOwnerRepository.save(accountOwner);
    }

    public void delete(final String individualTaxNumber) {
//        validate(individualTaxNumber);
        accountOwnerRepository.delete(getAccountOwner(individualTaxNumber));
    }

    public boolean individualTaxNumberExists(final String individualTaxNumber) {
//        validate(individualTaxNumber);
        return accountOwnerRepository.existsByIndividualTaxNumberIgnoreCase(individualTaxNumber);
    }

    public ReferencedWarning getReferencedWarning(final String individualTaxNumber) {
//        validate(individualTaxNumber);
        final AccountOwner accountOwner = getAccountOwner(individualTaxNumber);
        final BankAccount accountOwnerBankAccount = bankAccountRepository.findFirstByAccountOwner(accountOwner);
        if (accountOwnerBankAccount != null) {
            final ReferencedWarning referencedWarning = new ReferencedWarning();
            referencedWarning.setKey(individualTaxNumber);
            referencedWarning.addParam(" <= AccountOwner has bank account with IBAN:");
            referencedWarning.addParam(accountOwnerBankAccount.getIban());
//            referencedWarning.setParams(List.of(accountOwnerBankAccount.getIban()));
            return referencedWarning;
        }
        return null;
    }

    private AccountOwner getAccountOwner(String individualTaxNumber) {
//        validate(individualTaxNumber);
        return getAccountOwnerOptional(individualTaxNumber)
                .orElseThrow(() -> new NotFoundException("Account with individualTaxNumber = '" + individualTaxNumber + "' does not exist"));
    }

    private Optional<AccountOwner> getAccountOwnerOptional(String individualTaxNumber) {
        validate(individualTaxNumber);
        return accountOwnerRepository.findById(individualTaxNumber);
    }

    private void validate(String individualTaxNumber) {
        if (!individualTaxNumber.matches("\\d{12}")) {
            throw new IllegalArgumentException("'" + individualTaxNumber + "', individualTaxNumber must be 12 digits long");
        }
    }

    private AccountOwnerDTO mapToDTO(final AccountOwner accountOwner,
                                     final AccountOwnerDTO dto) {
        AccountOwnerDTO accountOwnerDTO = (dto != null) ? dto : new AccountOwnerDTO();
        accountOwnerDTO.setIndividualTaxNumber(accountOwner.getIndividualTaxNumber());
        accountOwnerDTO.setFirstName(accountOwner.getFirstName());
        accountOwnerDTO.setLastName(accountOwner.getLastName());
        return accountOwnerDTO;
    }

    private AccountOwner mapToEntity(final AccountOwnerDTO accountOwnerDTO,
                                     final AccountOwner accountOwner) {
        accountOwner.setFirstName(accountOwnerDTO.getFirstName().toUpperCase());
        accountOwner.setLastName(accountOwnerDTO.getLastName().toUpperCase());
        return accountOwner;
    }
}
