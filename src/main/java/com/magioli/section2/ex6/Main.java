package com.magioli.section2.ex6;

import com.magioli.section2.ex6.beans.Bike;
import com.magioli.section2.ex6.beans.Engine;
import com.magioli.section2.ex6.beans.Vehicle;
import com.magioli.section2.ex6.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static void main() {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        if (context.containsBean("engine")) {
            Engine engine = context.getBean(Engine.class);
            System.out.println("Engine name = " + engine.getName());
        }
        if (context.containsBean("vehicle")) {
            Vehicle v = context.getBean(Vehicle.class);
            System.out.println("Vehicle name = " + v.getName());
            System.out.println("Vehicle engine = " + v.getEngine());
        }
        if (context.containsBean("bike")) {
            Bike b = context.getBean(Bike.class);
            System.out.println("Bike model = " + b.getName());
        }
    }
}