package org.example.adventure_planner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for now
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users").permitAll() // allow register
                        .anyRequest().authenticated()          // everything else requires login
                )
                .formLogin(form -> form
                        .permitAll()                           // enable /login with default form
                )
                .logout(logout -> logout.permitAll());     // enable /logout

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
