package com.geekbang.rpc.demo.service;


import com.geekbang.rpc.demo.model.Order;

public interface OrderService {
    Order findById(Integer id);
    Order findError();
}
