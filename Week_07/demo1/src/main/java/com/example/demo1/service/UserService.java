package com.example.demo1.service;

import com.example.demo1.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> listUser();

    UserEntity findById(Long id);

    void insert(UserEntity user);

}
