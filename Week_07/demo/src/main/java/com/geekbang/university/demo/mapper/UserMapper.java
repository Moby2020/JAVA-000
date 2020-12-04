package com.geekbang.university.demo.mapper;

import com.geekbang.university.demo.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    void insertOne(UserEntity userEntity);

    List<Map<String, Object>> query(Map<String, Object> condition);
}
