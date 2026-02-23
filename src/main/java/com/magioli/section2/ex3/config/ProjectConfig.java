package com.magioli.section2.ex3.config;

import com.magioli.section2.ex3.beans.Person;
import com.magioli.section2.ex3.beans.Vehicle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public Vehicle vehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Toyota");
        return vehicle;
    }

    //by method invocation
//    @Bean
//    public Person person() {
//        Person person = new Person();
//        person.setName("Lucy");
//        person.setVehicle(vehicle());
//        return person;
//    }

    //by method parameter
    @Bean
    public Person person(Vehicle vehicle) {
        Person person = new Person();
        person.setName("Lucy");
        person.setVehicle(vehicle);
        return person;
    }
}
