package com.magioli.section2.ex3;

import com.magioli.section2.ex3.beans.Person;
import com.magioli.section2.ex3.beans.Vehicle;
import com.magioli.section2.ex3.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static void main() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Person person = context.getBean(Person.class);
        Vehicle vehicle = context.getBean(Vehicle.class);

        System.out.println("Person name from Spring Context is: " + person.getName());
        System.out.println("Vehicle name from Spring Context is: " + vehicle.getName());
        System.out.println("Vehicle that Person own is: " + person.getVehicle());
    }
}
