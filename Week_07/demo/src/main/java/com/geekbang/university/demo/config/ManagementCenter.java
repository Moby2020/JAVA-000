package com.geekbang.university.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class ManagementCenter {
    @Autowired
    @Qualifier("master")
    DataSource masterDataSource;

    @Autowired
    @Qualifier("slave")
    DataSource slaveDataSource;

    public DataSource getDefaultDataSource() {
        return masterDataSource;
    }

    public DataSource getSlaveDataSource() {
        return slaveDataSource;
    }
}
