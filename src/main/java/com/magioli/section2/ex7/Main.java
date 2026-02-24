package com.magioli.section2.ex7;

import com.magioli.section2.ex7.beans.MyService;
import com.magioli.section2.ex7.beans.UserSession;
import com.magioli.section2.ex7.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static void main() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        MyService myService1 = context.getBean(MyService.class);
        MyService myService2 = context.getBean(MyService.class);

        System.out.println(myService1.hashCode() == myService2.hashCode());

        UserSession userSession1 = context.getBean(UserSession.class);
        UserSession userSession2 = context.getBean(UserSession.class);

        System.out.println(userSession1.getSessionId() == userSession2.getSessionId());
    }
}
