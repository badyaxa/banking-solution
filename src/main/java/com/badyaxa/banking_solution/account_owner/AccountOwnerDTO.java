package com.badyaxa.banking_solution.account_owner;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountOwnerDTO {

    @Size(max = 12)
    @AccountOwnerIndividualTaxNumberValid
    private String individualTaxNumber;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

}
