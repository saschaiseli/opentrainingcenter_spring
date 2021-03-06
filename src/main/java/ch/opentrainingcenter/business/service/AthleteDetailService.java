package ch.opentrainingcenter.business.service;

import ch.opentrainingcenter.business.domain.Athlete;
import ch.opentrainingcenter.business.repositories.AthleteRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "AthleteDetailService")
public class AthleteDetailService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AthleteDetailService.class);
    @Autowired
    private AthleteRepo athleteRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        LOGGER.info("loadUserByUsername " + username);
        final Athlete athlete = athleteRepository.findByEmail(username);
        return athlete;
    }
}
