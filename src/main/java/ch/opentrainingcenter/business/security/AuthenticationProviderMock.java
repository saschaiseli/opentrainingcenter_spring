package ch.opentrainingcenter.business.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.repositories.AthleteRepository;

@Profile("!prod")
@Primary
@Service
public class AuthenticationProviderMock implements AuthenticationProvider {

	@Autowired
	private AthleteRepository athleteRepo;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		final Athlete athlete = getUser("sascha.iseli@gmx.ch");
		final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				athlete, athlete.getPassword(), athlete.getAuthorities());
		usernamePasswordAuthenticationToken.setDetails(athlete);
		return usernamePasswordAuthenticationToken;
	}

	private Athlete getUser(final String principal) {
		final Athlete athlete = athleteRepo.findByEmail(principal.toString());
		return athlete; // Return our own user class here!
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return true;
	}

}
