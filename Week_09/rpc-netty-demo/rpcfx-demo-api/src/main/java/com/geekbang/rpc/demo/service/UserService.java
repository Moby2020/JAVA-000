package com.geekbang.rpc.demo.service;


import com.geekbang.rpc.demo.model.User;

public interface UserService {
    User findById(Integer id);
}
