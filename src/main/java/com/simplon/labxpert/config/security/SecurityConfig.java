package com.simplon.labxpert.config.security;

import com.simplon.labxpert.config.security.filter.CustomAuthenticationFilter;
import com.simplon.labxpert.config.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/login/**", "/refreshToken/**").permitAll();
        // Admin can do everything
        http.authorizeRequests().antMatchers(GET, "/api/v1/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/v1/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/v1/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/api/v1/**").hasAnyAuthority("ADMIN");
        // Manager can do everything except delete
        http.authorizeRequests().antMatchers(GET, "/api/v1/**").hasAnyAuthority("MANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/v1/**").hasAnyAuthority("MANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/v1/**").hasAnyAuthority("MANAGER", "ADMIN");
        // Technician can only manage patients and samples (GET, POST, PUT)
        http.authorizeRequests().antMatchers(GET, "/api/v1/patients/**").hasAnyAuthority("TECHNICIAN", "MANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/v1/patients/**").hasAnyAuthority("TECHNICIAN", "MANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/v1/patients/**").hasAnyAuthority("TECHNICIAN", "MANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/v1/samples/**").hasAnyAuthority("TECHNICIAN", "MANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/v1/samples/**").hasAnyAuthority("TECHNICIAN", "MANAGER", "ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/v1/samples/**").hasAnyAuthority("TECHNICIAN", "MANAGER", "ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
