package com.example.demo.config;

import com.example.demo.server.RpcServer;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.OrderServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Autowired
    private ApplicationContext context;

    @Bean
    public RpcServer rpcServer() {
        return new RpcServer(context);
    }

    @Bean("com.example.demo.service.UserService")
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean("com.example.demo.service.OrderService")
    public OrderService orderService() {
        return new OrderServiceImpl();
    }
}
