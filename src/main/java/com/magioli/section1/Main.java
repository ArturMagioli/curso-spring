package com.magioli.section1;

import com.magioli.section1.beans.Vehicle;
import com.magioli.section1.config.ProjectConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static void main() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Audi");
        System.out.println("Vehicle name from non-spring Context is " + vehicle.getName());

        ApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Vehicle veh = context.getBean(Vehicle.class);
        System.out.println("Vehicle name from non-spring Context is " + veh.getName());

        String str = context.getBean(String.class);
        System.out.println("String value from Spring Context is: " + str);

        Integer luckyNumber = context.getBean(Integer.class);
        System.out.println("Integer value from Spring Context is: " + luckyNumber);

        String hello1 = (String) context.getBean("hello");
        System.out.println(hello1);
    }
}
