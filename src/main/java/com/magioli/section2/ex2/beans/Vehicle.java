package com.magioli.section2.ex2.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Vehicle implements InitializingBean, DisposableBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println("Printing Hello from Component Vehicle Bean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.name = "Audi";
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("I am being destroyed!");
    }

    //    Com Jakarta annotations:
//    @PostConstruct
//    public void initialize() {
//        this.name = "Audi";
//    }
//    @PreDestroy
//    public void lastWords() {
//        System.out.println("I am being destroyed!");
//    }
}
