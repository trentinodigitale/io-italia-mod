package it.tndigit.iot.config;

import it.tndigit.iot.security.SpringSecurityAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Mirko
 * Configuration class for the auditor provider
 *
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
public class PersistenceContext {

    @Bean
    AuditorAware< String > auditorProvider() {
        return new SpringSecurityAuditorAware();
    }



}
