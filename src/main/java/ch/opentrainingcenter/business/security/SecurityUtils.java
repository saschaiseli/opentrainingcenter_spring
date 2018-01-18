package ch.opentrainingcenter.business.security;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	private SecurityUtils() {

	}

	public static boolean isLoggedIn() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && authentication.isAuthenticated();
	}

	public static boolean hasRole(final String role) {
		final String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null
				&& authentication.getAuthorities().contains(new SimpleGrantedAuthority(roleWithPrefix));
	}

	public static boolean isAdmin() {

		return isLoggedIn() && hasRole("ADMIN");
	}
}
