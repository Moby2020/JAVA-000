package com.example.demo.datasource.hikari;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class HikariDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addBatch() {
        jdbcTemplate.batchUpdate("insert into student(id, name) values (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt, int i) throws SQLException {
                pstmt.setInt(1, i);
                pstmt.setString(2, "hName" + i);
            }

            @Override
            public int getBatchSize() {
                return 2;
            }
        });
    }

    public void queryAll() {
        List<String> students = jdbcTemplate.queryForList("select name from student", String.class);
        students.forEach(s -> log.info("student name is: {}", s));
    }

    public void update() {
        int update = jdbcTemplate.update("update student set name='hName' where id = 1");
        log.info("update count: {}", update);
    }
}
