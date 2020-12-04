package com.example.demo1.service;

import com.example.demo1.common.DataSourceSelector;
import com.example.demo1.common.DynamicDataSourceEnum;
import com.example.demo1.entity.UserEntity;
import com.example.demo1.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @DataSourceSelector(value = DynamicDataSourceEnum.SLAVE)
    @Override
    public List<UserEntity> listUser() {
        return userMapper.selectAll();
    }

    @DataSourceSelector(value = DynamicDataSourceEnum.SLAVE)
    @Override
    public UserEntity findById(Long id) {
        UserEntity user = new UserEntity();
        user.setId(id);
        return userMapper.selectByPrimaryKey(user);
    }

    @DataSourceSelector(value = DynamicDataSourceEnum.MASTER)
    @Override
    public void insert(UserEntity user) {
        userMapper.insert(user);
    }
}
