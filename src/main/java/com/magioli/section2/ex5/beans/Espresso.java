package com.magioli.section2.ex5.beans;

import org.springframework.stereotype.Component;

@Component
public class Espresso implements Coffee {

    @Override
    public String makeCoffe() {
        return "Espresso coffee";
    }
}
