package com.badyaxa.banking_solution.account_owner;

import com.badyaxa.banking_solution.util.ReferencedException;
import com.badyaxa.banking_solution.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountOwnerController {

    private final AccountOwnerService accountOwnerService;

    public AccountOwnerController(final AccountOwnerService accountOwnerService) {
        this.accountOwnerService = accountOwnerService;
    }

    @GetMapping("/account-owners")
    @Operation(summary = "Get all account owners",
                description = "Get a list of all account owners",
//            tags = {"Account Management"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of account owners")
            }
    )
    public ResponseEntity<List<AccountOwnerDTO>> getAllAccountOwners() {
        return ResponseEntity.ok(accountOwnerService.findAll());
    }

    @GetMapping("/account-owner/{individualTaxNumber}")
    @Operation(
            summary = "Get account owner by individual tax number",
            description = "Get account owner by individual tax number"
//            ,
//            tags = {"Account Management", "Account Owner"}
//            ,
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "List of all accounts")
//            }
    )
    public ResponseEntity<AccountOwnerDTO> getAccountOwner(
            @PathVariable(name = "individualTaxNumber") final String individualTaxNumber) {
        return ResponseEntity.ok(accountOwnerService.get(individualTaxNumber));
    }

    @PostMapping("/account-owner")
    @Operation(
            summary = "Create account owner",
            description = "Create account owner"
//            ,
//            tags = {"Account Management", "Account Owner"}
//            ,
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "List of all accounts")
//            }
    )
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createAccountOwner(
            @RequestBody @Valid final AccountOwnerDTO accountOwnerDTO) {
        final String createdIndividualTaxNumber = accountOwnerService.create(accountOwnerDTO);
        return new ResponseEntity<>('"' + createdIndividualTaxNumber + '"', HttpStatus.CREATED);
    }

    @PutMapping("/account-owner/{individualTaxNumber}")
    public ResponseEntity<String> updateAccountOwner(
            @PathVariable(name = "individualTaxNumber") final String individualTaxNumber,
            @RequestBody @Valid final AccountOwnerDTO accountOwnerDTO) {
        accountOwnerService.update(individualTaxNumber, accountOwnerDTO);
        return ResponseEntity.ok('"' + individualTaxNumber + '"');
    }

    @DeleteMapping("/account-owner/{individualTaxNumber}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAccountOwner(
            @PathVariable(name = "individualTaxNumber") final String individualTaxNumber) {
        final ReferencedWarning referencedWarning = accountOwnerService.getReferencedWarning(individualTaxNumber);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        accountOwnerService.delete(individualTaxNumber);
        return ResponseEntity.noContent().build();
    }

}
