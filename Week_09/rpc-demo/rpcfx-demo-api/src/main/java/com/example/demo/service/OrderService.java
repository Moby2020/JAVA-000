package com.example.demo.service;

import com.example.demo.model.Order;

public interface OrderService {
    Order findById(Integer id);
    Order findError();
}
