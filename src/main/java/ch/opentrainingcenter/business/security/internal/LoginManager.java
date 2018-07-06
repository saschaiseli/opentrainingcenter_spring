package ch.opentrainingcenter.business.security.internal;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.repositories.AthleteRepo;
import ch.opentrainingcenter.business.security.ILoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LoginManager implements ILoginManager {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AthleteRepo athleteRepo;

    @Autowired
    @Qualifier(value = "AthleteDetailService")
    private UserDetailsService userDetailsService;

    @Override
    public void doLogin(final String email, final String password) {
        final Athlete athlete = athleteRepo.findByEmailAndPassword(email, password);
        if (athlete != null) {
            loginSuccess(athlete, password);
        }
    }

    private void loginSuccess(final Athlete user, final String pw) {
        final Collection<? extends GrantedAuthority> auth = user.getAuthorities();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, pw,
                auth);

        authenticationManager.authenticate(token);
        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
            // principalHolder.setPrincipal(user);
        }
    }
}
