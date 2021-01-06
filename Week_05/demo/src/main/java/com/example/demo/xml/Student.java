package com.example.demo.xml;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class Student implements Serializable {
    private int id;
    private String name;
}
