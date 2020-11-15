package com.example.demo.factory;

import org.springframework.beans.factory.FactoryBean;

public class StudentFactory implements FactoryBean<Student02> {
    @Override
    public Student02 getObject() throws Exception {
        return new Student02(1, "Moby002");
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
