package com.example.demo0802.mapper;

import com.example.demo0802.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderMapper {
    void insertOne(Order order);

    void insertMany(List<Order> orders);

    List<Map<String, Object>> query(Map<String, Object> condition);
}
