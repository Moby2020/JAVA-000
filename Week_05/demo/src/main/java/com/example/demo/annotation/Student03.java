package com.example.demo.annotation;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class Student03 {
    private int id;
    private String name;
}
