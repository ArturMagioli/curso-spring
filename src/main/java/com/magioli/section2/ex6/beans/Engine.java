package com.magioli.section2.ex6.beans;

public class Engine {

    private String name;

    public Engine() {
        System.out.println("Engine bean created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                '}';
    }
}
