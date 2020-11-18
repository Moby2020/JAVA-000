package com.example.demo.datasource.jdbc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.*;

public class NativeJdbc {
    private static Connection connection = null;
    private PreparedStatement pstmt = null;

    public static void main(String[] args) throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        NativeJdbc jdbc = new NativeJdbc();
        connection = jdbc.getConnection();
        // 查询
        jdbc.queryAll();
        // 增加
        jdbc.insert();
        System.out.println("=======query after insert======");
        // 增加后查询
        jdbc.queryAll();
        // 更新
        jdbc.update();
        System.out.println(">>>>>>query after update>>>>>>");
        // 增加后查询
        jdbc.queryAll();
        // 关闭连接
        jdbc.close();
    }

    public Connection getConnection() {
        String url = "jdbc:h2:mem:dataSource";
        String user = "sa";
        String pwd = "";
        try {
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException throwables) {
            System.out.println("连接失败....");
            throwables.printStackTrace();
        }
        return connection;
    }

    public void queryAll() {
        String sql = "select * from student";
        try {
            if (connection != null) {
                pstmt = connection.prepareStatement(sql);
                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    System.out.println("Student name is " + name + ", id is " + id);
                }
            } else {
                throw new RuntimeException("查询失败，没有获取连接...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        if (connection != null) {
            try {
                PreparedStatement insertStmt = connection.prepareStatement("insert into student(id, name) values(3, 'insertTest')");
                insertStmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void update() {
        if (connection != null) {
            try {
                PreparedStatement updateStmt = connection.prepareStatement("update student set name = 'updateName' where id=1");
                updateStmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void close() throws SQLException {
        if (pstmt != null) {
            pstmt.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
