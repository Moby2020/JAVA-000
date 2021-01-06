package com.example.demo.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentStartTest implements CommandLineRunner {
    @Autowired
    private Student student;

    @Autowired
    private Klass klass;

    @Autowired
    private School school;

    public static void main(String[] args) {
        SpringApplication.run(StudentAutoConfiguration.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(student.toString());
        System.out.println(">>>>>分隔符>>>>>>>");
        klass.dong();
        System.out.println(">>>>>分隔符>>>>>>>");
        school.ding();
    }
}
