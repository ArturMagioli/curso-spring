package com.magioli.section2.ex6.beans;

public class Vehicle {

    private String name;
    private Engine engine;

    public Vehicle(Engine engine) {
        this.engine = engine;
        System.out.println("Vehicle bean created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Engine getEngine() {
        return engine;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                '}';
    }
}
