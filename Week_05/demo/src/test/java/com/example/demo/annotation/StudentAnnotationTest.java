package com.example.demo.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StudentAnnotationTest {

    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Student03.class);
        context.refresh();

        Student03 student03 = context.getBean(Student03.class);
        student03.setId(1);
        student03.setName("Moby003");
        System.out.println(student03);
    }
}
