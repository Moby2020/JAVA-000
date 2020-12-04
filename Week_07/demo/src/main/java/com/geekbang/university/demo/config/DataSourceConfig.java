package com.geekbang.university.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class DataSourceConfig {

    @Autowired
    Environment env;

    @Primary
    @Bean(name = "master")
    public DataSource masterDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("master.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("master.datasource.url"));
        dataSource.setUsername(env.getProperty("master.datasource.username"));
        dataSource.setPassword(env.getProperty("master.datasource.password"));
        return dataSource;
    }

    @Bean(name = "slave")
    public DataSource slaveDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("slave.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("slave.datasource.url"));
        dataSource.setUsername(env.getProperty("slave.datasource.username"));
        dataSource.setPassword(env.getProperty("slave.datasource.password"));
        return dataSource;
    }
}
