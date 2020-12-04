package com.geekbang.university.demo.service;

import com.geekbang.university.demo.config.ManagementCenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @Autowired
    ManagementCenter dataSourceCenter;
    @Autowired
    UserServiceImpl userService;

    @Test
    @Transactional
    public void testListAll() {
        /*String sql = "insert into user (name) VALUES (\"name103\");";
        // 使用主库 master
        userService.insertOne(dataSourceCenter.getDefaultDataSource(), sql);*/

        String querySql = "select * from user;";
        // 使用从库
        List<Map<String, Object>> entities = userService.query(dataSourceCenter.getSlaveDataSource(), querySql);
        for (Map item: entities) {
            System.out.println(item.toString());
        }
    }
}
