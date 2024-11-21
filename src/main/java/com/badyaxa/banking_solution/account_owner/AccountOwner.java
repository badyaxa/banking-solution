package com.badyaxa.banking_solution.account_owner;

import com.badyaxa.banking_solution.bank_account.BankAccount;
import com.badyaxa.banking_solution.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Entity
//@Table(name = "bank_task")
//@SuperBuilder
//@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class AccountOwner extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false, length = 12)
    @Pattern(regexp = "\\d{12}", message = "The individual tax number must contain only digits and be exactly 12 characters long.")
    private String individualTaxNumber;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "First name cannot be empty or contain spaces.")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 50 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-яІіЇїЄєґҐ'\\-]+$",
            message = "First name can only contain letters, apostrophes, and hyphens.")
    private String firstName;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Last name cannot be empty or contain spaces.")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 50 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-яІіЇїЄєґҐ'\\-]+$",
            message = "Last name can only contain letters, apostrophes, and hyphens.")
    private String lastName;

    @OneToMany(mappedBy = "accountOwner")
    private Set<BankAccount> accounts;
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "owner_accounts", joinColumns = @JoinColumn(name = "individual_tax_number"))
//    @Column(name = "accounts")
//    private Set<BankAccount> accounts = new HashSet<>();


    @PrePersist
    @PreUpdate
    private void format() {
        if (firstName != null) {
            firstName = firstName.toUpperCase();
        }
        if (lastName != null) {
            lastName = lastName.toUpperCase();
        }
    }

    protected
//    public
    AccountOwner() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public AccountOwner(String individualTaxNumber, String firstName, String lastName) {
        this.individualTaxNumber = individualTaxNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
