package com.magioli.section2.ex5.beans;

import org.springframework.stereotype.Component;

@Component("cappuccino")
//@Primary
public class Cappuccino implements Coffee {

    @Override
    public String makeCoffe() {
        return "Cappuccino coffee";
    }
}
