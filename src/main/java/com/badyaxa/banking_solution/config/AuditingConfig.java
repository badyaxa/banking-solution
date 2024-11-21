package com.badyaxa.banking_solution.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@EntityScan("com.badyaxa.banking_solution")
@EnableJpaRepositories("com.badyaxa.banking_solution")
@EnableTransactionManagement
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class AuditingConfig {

    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
}
