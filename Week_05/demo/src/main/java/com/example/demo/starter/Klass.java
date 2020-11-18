package com.example.demo.starter;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Klass {
    List<Student> students;

    public void dong() {
        System.out.println("student in klass " + this.getStudents());
    }
}
