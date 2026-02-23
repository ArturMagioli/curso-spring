package com.magioli.section2.ex4.beans;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car extends Vehicle {

    //@Autowired
    private Engine engine;

    public Car() {
        System.out.println("Car bean created");
    }

    public Engine getEngine() {
        return engine;
    }

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @PostConstruct
    public void initialize() {
        this.setName("Kia");
    }
}
