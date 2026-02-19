package com.magioli.section1.config;

import com.magioli.section1.beans.Vehicle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public Vehicle vehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Tesla");
        return vehicle;
    }

    @Bean
    String hello() {
        return "Hello World!";
    }

    @Bean
    Integer luckyNumber() {
        return 7;
    }
}
