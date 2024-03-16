package com.gestioncursos.gestioncursos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails user = User.builder()
                .username("user1")
                .password("{bcrypt}$2a$10$pgLQzFRo7Ngul2.E1T4N5O0A79v7OMOgPsqRSJ5wD5OIsX.2Xl64W")
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$pgLQzFRo7Ngul2.E1T4N5O0A79v7OMOgPsqRSJ5wD5OIsX.2Xl64W")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, user2);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/cursos").permitAll()
                        .requestMatchers("cursos/nuevo").hasRole("ADMIN")
                        .requestMatchers("/cursos/*","/cursos/delete/*").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(e -> e.accessDeniedPage("/403"));
        return httpSecurity.build();
    }
}
