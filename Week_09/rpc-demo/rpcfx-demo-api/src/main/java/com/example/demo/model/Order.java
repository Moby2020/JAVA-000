package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private Integer id;
    private String name;
    private Integer userId;
}
