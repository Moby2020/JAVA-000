package com.example.demo.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 老师，测试代码在 test 目录
 */
@Configuration
@EnableConfigurationProperties(StudentStarterProperties.class)
@ComponentScan
public class StudentAutoConfiguration {

    @Resource
    private StudentStarterProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public Student getStdent() {
        Student student = new Student();
        student.setId(properties.getSId());
        student.setName(properties.getSName());
        return student;
    }

    @Bean
    @ConditionalOnMissingBean
    public Klass getKlass() {
        Klass klass = new Klass();
        klass.setStudents(new ArrayList<>(Arrays.asList(getStdent())));
        return klass;
    }

    @Bean
    @ConditionalOnMissingBean
    public School getSchool() {
        School school = new School();
        school.setKlass(getKlass());
        school.setStudent100(getStdent());
        return school;
    }
}
