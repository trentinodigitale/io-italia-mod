package it.tndigit.iot.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware< String > {

	private Optional< String > auditor = Optional.of("SISTEMA");

	@Override
	public Optional< String > getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return auditor;
		}
		if (authentication.getPrincipal() instanceof UserDetails){
			return Optional.of(((UserDetails)authentication.getPrincipal()).getUsername());
		}
		return Optional.of(authentication.getPrincipal().toString());
	}
	

}
