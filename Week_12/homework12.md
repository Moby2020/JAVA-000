
### 必做作业：配置redis的主从复制，sentinel高可用，Cluster集群。
提交如下内容到github：
1）config配置文件
2）启动和操作、验证集群下数据读写的命令步骤


#### Redis主从复制

```
docker run -dit --name redis1 -p 6381:6379 -p 16381:16381 redis 
docker run -dit --name redis2 -p 6382:6379 -p 16382:16382 redis 
docker run -dit --name redis3 -p 6383:6379 -p 16383:16383 redis

# 从节点1
docker exec -ti redis2 redis-cli

127.0.0.1:6379> REPLICAOF 10.0.0.3 6381

127.0.0.1:6379> info replication
# Replication
role:slave
master_host:10.0.0.3
master_port:6381
master_link_status:up
master_last_io_seconds_ago:2
master_sync_in_progress:0
slave_repl_offset:56
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:58e41bc18ef95ce788f94579f83467c255d6c4a3
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:56
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:56

127.0.0.1:6379> exit


# 从节点2
docker exec -ti redis3 redis-cli

127.0.0.1:6379> REPLICAOF 10.0.0.3 6381

127.0.0.1:6379> info replication
# Replication
role:slave
master_host:10.0.0.3
master_port:6381
master_link_status:up
master_last_io_seconds_ago:4
master_sync_in_progress:0
slave_repl_offset:420
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:58e41bc18ef95ce788f94579f83467c255d6c4a3
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:420
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:379
repl_backlog_histlen:42

127.0.0.1:6379> exit

# 主节点
docker exec -ti redis1 redis-cli

127.0.0.1:6379> info replication
# Replication
role:master
connected_slaves:2
slave0:ip=172.17.0.1,port=6379,state=online,offset=84,lag=0
slave1:ip=172.17.0.1,port=6379,state=online,offset=84,lag=0
master_replid:84087d1ad1f44efc22a5155d1c26c6e08b634214
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:84
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:84
```

主节点写入操作：
```
docker exec -ti redis1 redis-cli

127.0.0.1:6379> keys *
(empty array)
127.0.0.1:6379> set key_name panpan
OK
127.0.0.1:6379> 
```

从节点 2 读取操作：
```
docker exec -ti redis3 redis-cli

127.0.0.1:6379> keys *
1) "key_name"
127.0.0.1:6379> get key_name
"panpan"
127.0.0.1:6379> 
```


#### 配置 Sentinel 哨兵

sentinel配置文件如下：
```
port 16381
daemonize no
pidfile /var/run/redis-sentinel.pid
logfile ""
dir /tmp
sentinel monitor mymaster 192.168.101.104 6381 2
sentinel down-after-milliseconds mymaster 10000
sentinel failover-timeout mymaster 30000
sentinel parallel-syncs mymaster 1
```

命令运行记录如下：
```
docker cp /etc/sentinel.conf redis1:/etc/sentinel.conf

42:X 06 Jan 2021 14:28:20.938 # +monitor master mymaster 10.0.0.3 6381 quorum 2
42:X 06 Jan 2021 14:28:20.941 * +slave slave 172.17.0.1:6379 172.17.0.1 6379 @ mymaster 10.0.0.3 6381
42:X 06 Jan 2021 14:28:30.951 # +sdown slave 172.17.0.1:6379 172.17.0.1 6379 @ mymaster 10.0.0.3 6381
```



