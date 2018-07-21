package ch.opentrainingcenter.business.security;

import ch.opentrainingcenter.business.domain.Athlete;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SecurityUtilsTest {
    @Before
    public void setUp() {
        final Athlete athlete = new Athlete("", "lastName", "email", "password");

        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(athlete,
                athlete.getPassword(), athlete.getAuthorities());
        token.setDetails(athlete);

        SecurityContextHolder.getContext().setAuthentication(token);

    }

    @Test
    public void testHasRole() {
        assertTrue(SecurityUtils.hasRole("ROLE_ADMIN"));
    }

    @Test
    public void testHasRoleWithoutPrefix() {
        assertTrue(SecurityUtils.hasRole("ADMIN"));
    }

    @Test
    public void testIsAdmin() {
        assertTrue(SecurityUtils.isAdmin());
    }

    @Test
    public void testIsLoggedIn() {
        assertTrue(SecurityUtils.isLoggedIn());
    }

    //
    @Test
    public void testHasNotRole() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertFalse(SecurityUtils.hasRole("ROLE_ADMIN"));
    }

    @Test
    public void testHasNotRoleWithoutPrefix() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertFalse(SecurityUtils.hasRole("ADMIN"));
    }

    @Test
    public void testIsNotAdmin() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertFalse(SecurityUtils.isAdmin());
    }

    @Test
    public void testIsNotLoggedIn() {
        SecurityContextHolder.getContext().setAuthentication(null);
        assertFalse(SecurityUtils.isLoggedIn());
    }
}
