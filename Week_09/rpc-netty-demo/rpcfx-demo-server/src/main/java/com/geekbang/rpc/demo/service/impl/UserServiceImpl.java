package com.geekbang.rpc.demo.service.impl;


import com.geekbang.rpc.demo.model.User;
import com.geekbang.rpc.demo.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User findById(Integer id) {
        return new User(id, "RPC");
    }
}
