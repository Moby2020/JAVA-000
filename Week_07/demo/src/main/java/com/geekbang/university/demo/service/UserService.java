package com.geekbang.university.demo.service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public interface UserService {
    void insertOne(DataSource dataSource, String sql);

    List<Map<String, Object>> query(DataSource dataSource, String sql);
}
