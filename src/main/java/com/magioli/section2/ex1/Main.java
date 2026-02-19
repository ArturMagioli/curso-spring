package com.magioli.section2.ex1;

import com.magioli.section2.ex1.beans.Vehicle;
import com.magioli.section2.ex1.config.ProjectConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static void main() {

        ApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Vehicle vehicle1 = context.getBean(Vehicle.class);

        System.out.println(vehicle1.getName());

        Integer number = context.getBean(Integer.class);

        System.out.println(number);
    }
}
