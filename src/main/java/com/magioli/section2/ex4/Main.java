package com.magioli.section2.ex4;

import com.magioli.section2.ex4.beans.Car;
import com.magioli.section2.ex4.beans.Engine;
import com.magioli.section2.ex4.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static void main() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Car car = context.getBean(Car.class);
        Engine engine = context.getBean(Engine.class);

        System.out.println("Car name from Spring Context is: " + car.getName());
        System.out.println("Engine name from Spring Context is: " + engine.getName());
        System.out.println("Engine that Car own is: " + car.getEngine());
    }
}
