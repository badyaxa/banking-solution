package com.badyaxa.banking_solution.account_owner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountOwnerDTO {

    @Pattern(regexp = "\\d{12}", message = "The individual tax number must contain only digits and be exactly 12 characters long.")
    @AccountOwnerIndividualTaxNumberValid
    private String individualTaxNumber;

    @NotBlank(message = "First name cannot be empty or contain spaces.")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 50 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-яІіЇїЄєґҐ'\\-]+$",
            message = "First name can only contain letters, apostrophes, and hyphens.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty or contain spaces.")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 50 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-яІіЇїЄєґҐ'\\-]+$",
            message = "Last name can only contain letters, apostrophes, and hyphens.")
    private String lastName;

}
