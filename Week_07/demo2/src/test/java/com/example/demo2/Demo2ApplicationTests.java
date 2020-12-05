package com.example.demo2;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class Demo2ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	ShardingMasterSlaveDataSource shardingMasterSlaveDataSource;

	@Test @Transactional
	public void test() throws SQLException {
		DataSource dataSource = shardingMasterSlaveDataSource.createDataSource();
		log.info("ShardingMasterSlaveDataSource info::" + dataSource.getConnection().getMetaData().getURL());

		Connection conn = dataSource.getConnection();
		Statement statement = conn.createStatement();

		String sql = "select * from user";
		statement.execute(sql);

		sql = "insert into user (name) VALUES (\"name104\");";
		statement.execute(sql);

		/**
		 * 插入主库后，再次查询发现使用的是主库？？？why?
		 */
		sql = "select * from user";
		statement.execute(sql);
	}
}
