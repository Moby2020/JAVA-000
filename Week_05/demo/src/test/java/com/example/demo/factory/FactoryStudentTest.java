package com.example.demo.factory;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FactoryStudentTest {

    @Test
    public void Test() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(StudentFactory.class);
        context.refresh();

        StudentFactory studentFactory = context.getBean(StudentFactory.class);
        Student02 student02 = studentFactory.getObject();
        System.out.println(student02);
    }
}
