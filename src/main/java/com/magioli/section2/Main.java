package com.magioli.section2;

import com.magioli.section2.beans.Vehicle;
import com.magioli.section2.config.ProjectConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static void main() {

        ApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Vehicle vehicle1 = context.getBean(Vehicle.class);

        System.out.println(vehicle1.getName());
    }
}
