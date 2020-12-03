### 主从复制
macOS 系统使用docker 拉取两个不同版本 MySQL 实现

拉取 mysql:5.7 作为主库，并创建 [my.cnf 文件](https://github.com/Moby2020/JAVA-000/tree/main/Week_07/my_master.cnf)
```
docker pull mysql:5.7
docker run -p 3506:3506 --name mysql -v /Users/moby/docker/mysql/conf:/etc/mysql/conf.d -v /Users/moby/docker/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7
```

拉取 mysql:5.7.25 作为从库，并创建 [my.cnf 文件](https://github.com/Moby2020/JAVA-000/tree/main/Week_07/my_slave.cnf)
```
docker pull mysql:5.7.25
docker run -p 3516:3516 --name mysql5725 -v /Users/moby/docker/mysql5725/conf:/etc/mysql/conf.d -v /Users/moby/docker/mysql5725/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7.25
```

登录主库
```SQL
docker exec -it mysql

mysql> show master status\G;
+------------------+----------+--------------+------------------+-------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+------------------+----------+--------------+------------------+-------------------+
| mysql-bin.000003 |      305 |              |                  |                   |
+------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)
```

登录从库
```SQL
docker exec -it mysql5725

mysql> CHANGE MASTER TO MASTER_HOST='192.168.1.137',MASTER_PORT=3506,MASTER_USER='root',MASTER_PASSWORD='123456',MASTER_LOG_FILE='mysql-bin.000008',MASTER_LOG_POS=154;

mysql> start slave;
```

主从测试
在主库中，新建一张表并插入一条数据，在从库中查询可以看到
```SQL
mysql> use db;
mysql> create table t1(id int not null auto_increment,name varchar(50) not null default '',primary key(id))ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
mysql> insert into t1(id,name) vaules(1,'Moby');
```

从库中查询
```SQL
mysql> use db;
mysql> select * from t1;
```
