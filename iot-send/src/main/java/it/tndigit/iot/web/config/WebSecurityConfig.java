package it.tndigit.iot.web.config;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import it.tndigit.iot.domain.ServizioPO;
import it.tndigit.iot.repository.ServizioRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${spring.security.oauth2.resourceserver.id}") 
	private String resourceId;
    @Autowired
    protected ServizioRepository servizioRepository;
	
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.cors();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        httpSecurity.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "**/favicon.ico").anonymous()
                .antMatchers("/api/v1/servizio/**").permitAll()
                .antMatchers("/api/v1/**").authenticated()
                .antMatchers("/swagger-ui.html/**", "/actuator/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2ResourceServer().jwt();
    }    

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ITALY);
        return localeResolver;
    }

    @Bean
    public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(properties.getJwt().getJwkSetUri()).build();
         
        // VALIDATE TOKEN TIMESTAMP AND ISSUER
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(properties.getJwt().getIssuerUri());
        // VALIDATE AUDIENCE AND SERVICE
        OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator();
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);        
        return jwtDecoder;
    }    

    
    public class JwtAudienceValidator implements OAuth2TokenValidator<Jwt> {
    	
    	private final OAuth2Error audienceError = new OAuth2Error("401", "Invalid audience", null);
    	private final OAuth2Error serviceError = new OAuth2Error("401", "Invalid service", null);

    	@Override
    	public OAuth2TokenValidatorResult validate(Jwt token) {
    		// validate audience: should contain resource service id
    		if (!token.getClaimAsStringList(JwtClaimNames.AUD).contains(resourceId)) {
    			return OAuth2TokenValidatorResult.failure(audienceError);
    		}
    		// client ID as of OAuth2.0
    		String clientId = token.getSubject();
    		// check service is present
            Optional<ServizioPO> servizioPOOptional = servizioRepository.findAllByCodiceIdentificativo(clientId);
            if (!servizioPOOptional.isPresent()) {
    			return OAuth2TokenValidatorResult.failure(serviceError);
            }
            return OAuth2TokenValidatorResult.success();
    	}

    }
}