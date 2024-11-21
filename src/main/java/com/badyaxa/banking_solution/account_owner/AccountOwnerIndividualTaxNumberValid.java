package com.badyaxa.banking_solution.account_owner;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Check that individualTaxNumber is present and available when a new AccountOwner is created.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = AccountOwnerIndividualTaxNumberValid.AccountOwnerIndividualTaxNumberValidValidator.class
)
public @interface AccountOwnerIndividualTaxNumberValid {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class AccountOwnerIndividualTaxNumberValidValidator implements ConstraintValidator<AccountOwnerIndividualTaxNumberValid, String> {

        private final AccountOwnerService accountOwnerService;
        private final HttpServletRequest request;

        public AccountOwnerIndividualTaxNumberValidValidator(
                final AccountOwnerService accountOwnerService,
                final HttpServletRequest request) {
            this.accountOwnerService = accountOwnerService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("individualTaxNumber");
            if (currentId != null) {
                // only relevant for new objects
                return true;
            }
            String error = null;
            if (value == null) {
                // missing input
                error = "NotNull";
            } else if (accountOwnerService.individualTaxNumberExists(value)) {
                error = "Exists.accountOwner.individualTaxNumber";
            }
            //@Pattern(regexp = "\\d{12}", message = "The individual tax number must contain only digits and be exactly 12 characters long.")
            else if (!value.matches("\\d{12}")) {
                error = "Pattern.accountOwner.individualTaxNumber";
            }
            if (error != null) {
                cvContext.disableDefaultConstraintViolation();
                cvContext.buildConstraintViolationWithTemplate("{" + error + "}")
                        .addConstraintViolation();
                return false;
            }
            return true;
        }

    }

}
