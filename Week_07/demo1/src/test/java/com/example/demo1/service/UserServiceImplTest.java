package com.example.demo1.service;

import com.example.demo1.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    public void listAll() {
        List<UserEntity> user = userService.listUser();
        user.forEach(userEntity -> {
            String name = userEntity.getName();
            System.out.println("user name: " + name);
        });
    }

    @Test
    public void findById() {
        UserEntity entity = userService.findById(3L);
        System.out.println("findById: " + entity.getName());
    }

    @Test
    public void insertTest() {
        UserEntity entity = new UserEntity();
        entity.setId(4L);
        entity.setName("Bob Dylan");
        userService.insert(entity);
    }
}
