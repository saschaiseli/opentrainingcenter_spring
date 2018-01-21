package ch.opentrainingcenter.business.security;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ch.opentrainingcenter.business.domain.Athlete;

@Component
@Scope(value = "session")
public class PrincipalHolder {
	private Athlete principal;

	public Athlete getPrincipal() {
		return principal;
	}

	public void setPrincipal(final Athlete principal) {
		this.principal = principal;
	}
}
