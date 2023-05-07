package com.habib.Drugs.security;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor

public class SecurityConfig {
    @Autowired
    private  PasswordEncoder passwordEncoder;

    private final AuthenticationProvider authenticationProvider;




    //l'utilisation de l'annotation bean au demarage spring va appeler la methode securityFilterChain()
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("Passwors Encoded BCRYPT :******************** ");
        System.out.println(passwordEncoder.encode("1234"));
        httpSecurity.formLogin().loginPage("/login").permitAll()
                .and()
                .authenticationProvider(authenticationProvider);
        httpSecurity.authorizeRequests().requestMatchers("/webjars/**").permitAll();
        httpSecurity.authorizeRequests().requestMatchers("/api/v1/users/**").permitAll();

        httpSecurity.authorizeRequests().requestMatchers("/showCreate").hasAnyRole("ADMIN","AGENT");
        httpSecurity.authorizeRequests().requestMatchers("/saveDrug").hasAnyRole("ADMIN","AGENT");
        httpSecurity.authorizeRequests().requestMatchers("/ListeDrugs");
        httpSecurity.authorizeRequests().requestMatchers("/supprimerDrug","/modifierDrug","/updateDrug") .hasAnyRole("ADMIN");
        httpSecurity.authorizeRequests().anyRequest().authenticated();
        httpSecurity.formLogin().defaultSuccessUrl("/ListeDrugs",true);

        httpSecurity.exceptionHandling().accessDeniedPage("/accessDenied");
        httpSecurity.csrf().disable(); // disable CSRF protection

        return httpSecurity.build();
    }
}