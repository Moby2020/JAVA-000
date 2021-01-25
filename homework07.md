### 作业 1、插入 100 万订单模拟数据，测试不同方法插入效率
1. 使用 procedure
```SQL
delimiter //
DROP PROCEDURE IF EXISTS insert_tab_products;
CREATE PROCEDURE `insert_tab_products`()
begin
	declare i int default 1;
	declare t long default 1540135964091;
	declare n varchar(100) default 'n';
	start transaction;
	while i < 1000001
	do
		insert into products values(i, i, 1*i, 1*i, concat(n, i), concat(n, i), '', t, t);
		set i = i+1;
		set t = t+1;
		end while;
	commit;
	end //
```
消耗时间：33.35sec

![imgage](./imgs/time1.png)

插入数据：100 万：

![image](./imgs/data1.png)

导出SQL
```SQL
-- mysqldump -uroot -p shop_mall products > products.sql -- 此法导入不方便
select * from products into outfile '/Users/moby/products.txt' fields terminated by ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n';
```

2. 使用 load data 方式
```SQL
load data infile '/Users/moby/products.txt' into table products fields terminated by ',' ENCLOSED BY '"' lines terminated by '\r\n';
```
消耗时间：6.69sec

![imgage](./imgs/time2.png)

```
load data遇到的问题：
（ERROR1366: Incorrect integer value）
解决：更改 session sql_mode的值(去掉STRICT_TRANS_TABLES)
set session sql_mode = 'ONLY_FULL_GROUP_BY,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
```
![imgage](./imgs/strict.png)

### 作业 2、读写分离，动态实现数据库切换
[代码路径](https://github.com/Moby2020/JAVA-000/tree/main/Week_07/demo1/src/main)
[测试路径](https://github.com/Moby2020/JAVA-000/blob/main/Week_07/demo1/src/test/java/com/example/demo1/service/UserServiceImplTest.java)


数据库切换
![imgage](./imgs/toSlave.png)
![imgage](./imgs/toMaster.png)

查询所有用户
![imgage](./imgs/listAll.png)

插入结果
![imgage](./imgs/insert.png)


### 作业 3、读写分离，数据库框架版本2.0
[代码路径](https://github.com/Moby2020/JAVA-000/blob/main/Week_07/demo2/src/main/java/com/example/demo2/ShardingMasterSlaveDataSource.java)
[测试路径](https://github.com/Moby2020/JAVA-000/blob/main/Week_07/demo2/src/test/java/com/example/demo2/Demo2ApplicationTests.java)

测试截图
![imgage](./imgs/03.png)


### 补交第六周作业（如果有用的话）
[week06](https://github.com/Moby2020/JAVA-000/blob/main/Week_06/SQL.md)

