package com.magioli.section2.ex5.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoffeeShop {

    private final Coffee coffee;

    @Autowired
    public CoffeeShop(Coffee coffee) {
        this.coffee = coffee;
    }

    public Coffee getCoffe() {
        return coffee;
    }
}
