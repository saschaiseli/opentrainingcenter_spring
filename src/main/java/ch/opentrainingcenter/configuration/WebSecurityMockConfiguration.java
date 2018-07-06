package ch.opentrainingcenter.configuration;

import ch.opentrainingcenter.business.security.AuthenticationProviderMock;
import com.vaadin.spring.annotation.EnableVaadin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.vaadin.spring.http.HttpService;
import org.vaadin.spring.security.annotation.EnableVaadinSharedSecurity;
import org.vaadin.spring.security.config.VaadinSharedSecurityConfiguration;
import org.vaadin.spring.security.shared.VaadinAuthenticationSuccessHandler;
import org.vaadin.spring.security.shared.VaadinSessionClosingLogoutHandler;
import org.vaadin.spring.security.shared.VaadinUrlAuthenticationSuccessHandler;
import org.vaadin.spring.security.web.VaadinRedirectStrategy;

@Profile("dev")
@Configuration
@EnableVaadin
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
@EnableVaadinSharedSecurity
@EnableJpaAuditing
@ComponentScan
public class WebSecurityMockConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityMockConfiguration.class);

    @Autowired
    private AuthenticationProviderMock mock;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(mock);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        LOGGER.info("******************************************************************************************");
        LOGGER.info("******************** DEV MODE *****************************************************");
        LOGGER.info("******************************************************************************************");

        http.authorizeRequests().antMatchers("/THEME", "/VAADIN/**", "/PUSH/**", "/UIDL/**", "/statistic", "/login",
                "/error/**", "/accessDenied/**", "/vaadinServlet/**").permitAll();

        http.httpBasic().disable();
        http.formLogin().disable();

        http.logout().addLogoutHandler(new VaadinSessionClosingLogoutHandler()).logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout").permitAll();

        http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

        http.csrf().disable();
        http.authenticationProvider(mock);

        http.sessionManagement().sessionAuthenticationStrategy(sessionAuthenticationStrategy());
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new SessionFixationProtectionStrategy();
    }

    @Bean(name = VaadinSharedSecurityConfiguration.VAADIN_AUTHENTICATION_SUCCESS_HANDLER_BEAN)
    public VaadinAuthenticationSuccessHandler vaadinAuthenticationSuccessHandler(final HttpService httpService,
                                                                                 final VaadinRedirectStrategy vaadinRedirectStrategy) {
        return new VaadinUrlAuthenticationSuccessHandler(httpService, vaadinRedirectStrategy, "/");
    }
}
