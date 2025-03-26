package org.lessons.java.ticket_platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    @SuppressWarnings("removal")
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()

                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                .requestMatchers("/tickets/create", "/tickets/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/tickets/**").hasAuthority("ADMIN")
                .requestMatchers( "/notes/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/notes/edit/**", "notes/delete/**").hasAuthority("ADMIN")

                .requestMatchers("/tickets", "/tickets/**", "/tickets/{id}/note", "/notes/create")
                .hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/notes/create")
                .hasAnyAuthority("USER", "ADMIN")

                .anyRequest().permitAll();


                http.formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/tickets", true)
                    .permitAll()
                );
    
                http.logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                );
    
                http.exceptionHandling(exception -> exception
                    .accessDeniedPage("/access-denied")
                );

        return http.build();
    }

}
