package com.example.demo0803;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Demo0803Application {

    public static void main(String[] args) throws IOException, SQLException {
        DataSource dataSource = getShardingDatasource();
        // 先清理表中的数据
        cleanupData(dataSource);

        TransactionTypeHolder.set(TransactionType.XA);

        Connection conn = dataSource.getConnection();
        String sql = "insert into t_order (user_id, order_id) VALUES (?, ?);";
        System.out.println("First XA Start insert data");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (int i = 1; i < 11; i++) {
                pstmt.setLong(1, i);
                pstmt.setLong(2, i);
                pstmt.executeUpdate();
            }
            conn.commit();
        }
        System.out.println("First XA insert successful");

        System.out.println("===**===**===**===分割线===**===**===**===");

        String querySql = "select * from t_order";
        try (PreparedStatement statement = conn.prepareStatement(querySql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                System.out.println("orderId: " + orderId);
            }
        }
    }

    private static void cleanupData(DataSource dataSource) {
        System.out.println("Delete all Data");
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement();) {
            stmt.execute("delete from t_order");
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Delete all Data successful");
    }

    private static DataSource getShardingDatasource() throws IOException, SQLException {
        String fileName = "/Users/moby/geek_university/JAVA-000/Week_08/demo0803/src/main/resources/config-sharding.yaml";
        File yamlFile = new File(fileName);
        return YamlShardingSphereDataSourceFactory.createDataSource(yamlFile);
    }

}
