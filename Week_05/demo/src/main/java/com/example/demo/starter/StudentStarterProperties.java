package com.example.demo.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "myproper")
public class StudentStarterProperties {
    private int sId = 1;
    private String sName = "test";
}
