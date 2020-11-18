package com.example.demo.starter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class School implements ISchool {

    @Autowired
    private Klass klass;
    @Autowired
    private Student student100;

    @Override
    public void ding() {
        System.out.println("Class1 have " + this.klass.getStudents().size() + " students and one is " + this.student100);
    }
}
