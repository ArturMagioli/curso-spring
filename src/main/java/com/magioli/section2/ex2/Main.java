package com.magioli.section2.ex2;

import com.magioli.section2.ex2.beans.Vehicle;
import com.magioli.section2.ex2.config.ProjectConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static void main() {

        AnnotationConfigApplicationContext  context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Vehicle vehicle = context.getBean(Vehicle.class);
        System.out.println("Vehicle name from Spring Context is " + vehicle.getName());
        vehicle.sayHello();
        context.close();
    }
}
