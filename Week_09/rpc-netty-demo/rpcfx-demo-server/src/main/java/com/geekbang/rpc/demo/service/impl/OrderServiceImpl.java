package com.geekbang.rpc.demo.service.impl;


import com.geekbang.rpc.demo.exception.CustomException;
import com.geekbang.rpc.demo.model.Order;
import com.geekbang.rpc.demo.service.OrderService;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findById(Integer id) {
        return new Order(1, "RPC", 1);
    }

    @Override
    public Order findError() {
        throw new CustomException("Custom exception");
    }
}
