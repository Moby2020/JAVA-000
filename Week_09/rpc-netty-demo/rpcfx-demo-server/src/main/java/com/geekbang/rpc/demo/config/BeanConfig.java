package com.geekbang.rpc.demo.config;

import com.geekbang.rpc.demo.service.OrderService;
import com.geekbang.rpc.demo.service.UserService;
import com.geekbang.rpc.demo.service.impl.OrderServiceImpl;
import com.geekbang.rpc.demo.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean("com.geekbang.rpc.demo.service.UserService")
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean("com.geekbang.rpc.demo.service.OrderService")
    public OrderService orderService() {
        return new OrderServiceImpl();
    }
}
