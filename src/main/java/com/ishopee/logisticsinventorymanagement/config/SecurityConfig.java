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
                .antMatchers("/userinfo/register", "/userinfo/save").hasAuthority("ADMIN")
                .antMatchers("/userinfo/**").permitAll()
                .antMatchers("/analytics/**", "/api/**", "/doc/**", "/swagger-ui.html", "/charts/**").permitAll()
                .antMatchers("/st/**", "/mus/**", "/part/**").hasAnyAuthority("ADMIN", "APPUSER")
                .antMatchers("/po/**", "/so/**", "/dnp/**", "/pu/**", "/om/**").hasAuthority("APPUSER")
                .anyRequest()
                .authenticated()

                // Form Login
                .and()
                .formLogin()
                .loginPage("/userinfo/login") //GET Method
                .loginProcessingUrl("/login")    // POST Method(Save)
                .defaultSuccessUrl("/userinfo/setup", true)
                .failureUrl("/userinfo/login?invalidCredentials")

                // Logout Details
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/userinfo/login?logout")

                // Exception Details
                .and()
                .exceptionHandling()
                .accessDeniedPage("/userinfo/login?denied");
    }
}
