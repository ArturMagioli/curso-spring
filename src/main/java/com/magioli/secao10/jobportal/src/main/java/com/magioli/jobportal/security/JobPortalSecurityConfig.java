package com.magioli.jobportal.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JobPortalSecurityConfig {

    @Qualifier("publicPaths")
    private final List<String> publicPaths;

    @Qualifier("securedPaths")
    private final List<String> securedPaths;

    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http) {
        return http.csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests((requests) -> {
                    publicPaths.forEach(path -> requests.requestMatchers(path).permitAll());
                    securedPaths.forEach(path -> requests.requestMatchers(path).authenticated());
                    requests.anyRequest().denyAll();
                })
//                        requests.requestMatchers("/api/companies/public").permitAll()
//                                .requestMatchers("/api/contacts/public").permitAll()
//                    requests.requestMatchers(RegexRequestMatcher.regexMatcher(".*public$")).permitAll()
//                            .requestMatchers("/api/swagger-ui.html",
//                                    "/swagger-ui/**",
//                                    "/api/v3/api-docs/**",
//                                    "/swagger-resources/**",
//                                    "/swagger-ui.html",
//                                    "/webjars/**").permitAll())
                .formLogin(flc -> flc.disable())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
