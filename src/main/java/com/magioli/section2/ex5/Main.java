package com.magioli.section2.ex5;

import com.magioli.section2.ex5.beans.Coffee;
import com.magioli.section2.ex5.beans.CoffeeShop;
import com.magioli.section2.ex5.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static void main() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        CoffeeShop coffeeShop = context.getBean(CoffeeShop.class);
        Coffee coffee = coffeeShop.getCoffe();
        System.out.println(coffee.makeCoffe());
    }
}
