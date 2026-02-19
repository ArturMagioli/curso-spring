package com.magioli.section2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnotherProjectConfig {

    @Bean
    Integer luckyNumber() {
        return 16;
    }

    @Bean
    String hello() {
        return "Hello, World!";
    }
}
