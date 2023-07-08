package com.ishopee.logisticsinventorymanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class
SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http.authorizeRequests()
                .antMatchers("/userinfo/**").hasAuthority("ADMIN")
                .antMatchers("/st/**", "/mus/**", "/part/**").hasAnyAuthority("ADMIN", "APPUSER")
                .antMatchers("/po/**", "/so/**", "/dnp/**", "/pu/**", "/om/**").hasAuthority("APPUSER")
                .antMatchers("/analytics/**", "/api/**", "/doc/**").permitAll()
                .anyRequest()
                .authenticated()

                // Form Login
                .and()
                .formLogin()
                .defaultSuccessUrl("/st/all", true)

                // Logout Details
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                // Exception Details
                .and()
                .exceptionHandling()
                .accessDeniedPage("/userinfo/denied");
    }
}
