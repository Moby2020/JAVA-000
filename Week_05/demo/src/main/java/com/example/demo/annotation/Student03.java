package com.example.demo.annotation;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
@ToString
public class Student03 implements Serializable {
    private int id;
    private String name;
}
