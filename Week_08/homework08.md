### （必做一）对订单表数据进行水平分库分表，拆分2个库，每个库16张表
[代码]()

SQL
```SQL
CREATE TABLE IF NOT EXISTS `t_order` (
    `order_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
```

[配置文件]()

### （必做二）基于hmily TCC或ShardingSphere的Atomikos XA实现一个简单的分布式事务应用demo（二选一），提交到github。
