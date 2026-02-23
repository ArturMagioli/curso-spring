package com.magioli.section2.ex3.beans;

public class Vehicle {
    private String name;

    public Vehicle() {
        System.out.println("Vehicle bean created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
