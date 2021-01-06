### （必做一）对订单表数据进行水平分库分表，拆分2个库，每个库16张表
[代码](https://github.com/Moby2020/JAVA-000/tree/main/Week_08/demo0802)

SQL
```SQL
CREATE TABLE IF NOT EXISTS `t_order` (
    `order_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
```

[配置文件](https://github.com/Moby2020/JAVA-000/tree/main/Week_08/conf)

config-sharding.yaml
```
schemaName: sharding_db

dataSourceCommon:
  username: root
  password: root
  connectionTimeoutMilliseconds: 30000
  idleTimeoutMilliseconds: 60000
  maxLifetimeMilliseconds: 1800000
  maxPoolSize: 50
  minPoolSize: 1
  maintenanceIntervalMilliseconds: 30000
#
dataSources:
  ds_0:
    url: jdbc:mysql://127.0.0.1:3311/demo_ds_0?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
  ds_1:
    url: jdbc:mysql://127.0.0.1:3312/demo_ds_1?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true

rules:
- !SHARDING
  tables:
    t_order:
      actualDataNodes: ds_${0..1}.t_order_${0..15}
      tableStrategy:
        standard:
          shardingColumn: order_id
          shardingAlgorithmName: t_order_inline

  defaultDatabaseStrategy:
    standard:
      shardingColumn: user_id
      shardingAlgorithmName: database_inline
 
  shardingAlgorithms:
    database_inline:
      type: INLINE
      props:
        algorithm-expression: ds_${user_id % 2}
    t_order_inline:
      type: INLINE
      props:
        algorithm-expression: t_order_${order_id % 16}
```

server.yaml
```
authentication:
  users:
    root:
      password: root
    sharding:
      password: sharding 
      authorizedSchemas: sharding_db

props:
  max-connections-size-per-query: 1
  acceptor-size: 16  # The default value is available processors count * 2.
  executor-size: 16  # Infinite by default.
  proxy-frontend-flush-threshold: 128  # The default value is 128.
    # LOCAL: Proxy will run with LOCAL transaction.
    # XA: Proxy will run with XA transaction.
    # BASE: Proxy will run with B.A.S.E transaction.
  proxy-transaction-type: LOCAL
  proxy-opentracing-enabled: false
  proxy-hint-enabled: false
  query-with-cipher-column: true
  sql-show: true
  check-table-metadata-enabled: false
```

### （必做二）基于ShardingSphere的Atomikos XA实现一个简单的分布式事务应用

数据库配置
```
dataSources:
  ds_0: !!com.zaxxer.hikari.HikariDataSource
    driverClassName: com.mysql.cj.jdbc.Driver
    jdbcUrl: jdbc:mysql://127.0.0.1:3311/db_0?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: root
    autoCommit: false
  ds_1: !!com.zaxxer.hikari.HikariDataSource
    driverClassName: com.mysql.cj.jdbc.Driver
    jdbcUrl: jdbc:mysql://127.0.0.1:3312/db_1?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: root
    autoCommit: false

rules:
  - !SHARDING
    tables:
      t_order:
        actualDataNodes: ds_${0..1}.t_order_${0..1}
        databaseStrategy:
          standard:
            shardingColumn: user_id
            shardingAlgorithmName: database_inline
        tableStrategy:
          standard:
            shardingColumn: order_id
            shardingAlgorithmName: t_order_inline
    bindingTables:
      - t_order

    shardingAlgorithms:
      database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${user_id % 2}
      t_order_inline:
        type: INLINE
        props:
          algorithm-expression: t_order_${order_id % 2}

props:
  sql-show: true
```

[作业连接](https://github.com/Moby2020/JAVA-000/blob/main/Week_08/demo0803/src/main/java/com/example/demo0803/Demo0803Application.java)
