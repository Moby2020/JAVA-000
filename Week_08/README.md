学习笔记

### Sharding-Sphere Proxy 分库分表简单示例

#### 参考链接
[](https://github.com/lw1243925457/SE-Notes/blob/master/profession/program/java/shardingsphere/ShardingSphereProxy%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8.md)

#### 简要说明
对一张简单的订单表数据表进行水平分库分表，拆分2个库，每个库16张表并在新结构在演示常见的增删改查操作

#### 环境配置
使用 docker 启动两个 MySQL
```
docker run --name mysql11 -p 3311:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_ROOT_HOST=% -d mysql:latest
docker run --name mysql12 -p 3312:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_ROOT_HOST=% -d mysql:latest
```

下载[apache-shardingsphere-5.0.0-alpha-shardingsphere-jdbc-bin.tar.gz](https://www.apache.org/dyn/closer.cgi/shardingsphere/5.0.0-alpha/apache-shardingsphere-5.0.0-alpha-shardingsphere-proxy-bin.tar.gz)

下载[mysql-connector-java-5.1.47.jar](https://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.47/mysql-connector-java-5.1.47.jar) 下载完成后将 jar 文件放到 Sharding 根目录的lib 目录下

配置：server.yaml、config-sharding.yaml, 如下:
server.yaml
```
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

######################################################################################################
# 
# If you want to configure governance, authorization and proxy properties, please refer to this file.
# 
######################################################################################################
#
#governance:
#  name: governance_ds
#  registryCenter:
#    type: ZooKeeper
#    serverLists: localhost:2181
#    props:
#      retryIntervalMilliseconds: 500
#      timeToLiveSeconds: 60
#      maxRetries: 3
#      operationTimeoutMilliseconds: 500
#  overwrite: false

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


```
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

######################################################################################################
# 
# Here you can configure the rules for the proxy.
# This example is configuration of sharding rule.
# 
######################################################################################################
#
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
#      keyGenerateStrategy:
#        column: order_id
#        keyGeneratorName: snowflake
#    t_order_item:
#      actualDataNodes: ds_${0..1}.t_order_item_${0..1}
#      tableStrategy:
#        standard:
#          shardingColumn: order_id
#          shardingAlgorithmName: t_order_item_inline
#      keyGenerateStrategy:
#        column: order_item_id
#        keyGeneratorName: snowflake
#  bindingTables:
#    - t_order,t_order_item
  defaultDatabaseStrategy:
	standard:
		shardingColumn: user_id
		shardingAlgorithmName: database_inline
#  defaultTableStrategy:
#    none:
#  
  shardingAlgorithms:
	database_inline:
		type: INLINE
	props:
		algorithm-expression: ds_${user_id % 2}
	t_order_inline:
		type: INLINE
	props:
		algorithm-expression: t_order_${order_id % 16}
#    t_order_item_inline:
#      type: INLINE
#      props:
#        algorithm-expression: t_order_item_${order_id % 2}
#  
#  keyGenerators:
#    snowflake:
#      type: SNOWFLAKE
#      props:
#        worker-id: 123

######################################################################################################
#
# If you want to connect to MySQL, you should manually copy MySQL driver to lib directory.
#
######################################################################################################

#schemaName: sharding_db
#
#dataSourceCommon:
#  username: root
#  password:
#  connectionTimeoutMilliseconds: 30000
#  idleTimeoutMilliseconds: 60000
#  maxLifetimeMilliseconds: 1800000
#  maxPoolSize: 50
#  minPoolSize: 1
#  maintenanceIntervalMilliseconds: 30000
#
#dataSources:
#  ds_0:
#    url: jdbc:mysql://127.0.0.1:3306/demo_ds_0?serverTimezone=UTC&useSSL=false
#  ds_1:
#    url: jdbc:mysql://127.0.0.1:3306/demo_ds_1?serverTimezone=UTC&useSSL=false
#
#rules:
#- !SHARDING
#  tables:
#    t_order:
#      actualDataNodes: ds_${0..1}.t_order_${0..1}
#      tableStrategy:
#        standard:
#          shardingColumn: order_id
#          shardingAlgorithmName: t_order_inline
#      keyGenerateStrategy:
#        column: order_id
#        keyGeneratorName: snowflake
#    t_order_item:
#      actualDataNodes: ds_${0..1}.t_order_item_${0..1}
#      tableStrategy:
#        standard:
#          shardingColumn: order_id
#          shardingAlgorithmName: t_order_item_inline
#      keyGenerateStrategy:
#        column: order_item_id
#        keyGeneratorName: snowflake
#  bindingTables:
#    - t_order,t_order_item
#  defaultDatabaseStrategy:
#    standard:
#      shardingColumn: user_id
#      shardingAlgorithmName: database_inline
#  defaultTableStrategy:
#    none:
#  
#  shardingAlgorithms:
#    database_inline:
#      type: INLINE
#      props:
#        algorithm-expression: ds_${user_id % 2}
#    t_order_inline:
#      type: INLINE
#      props:
#        algorithm-expression: t_order_${order_id % 2}
#    t_order_item_inline:
#      type: INLINE
#      props:
#        algorithm-expression: t_order_item_${order_id % 2}
#  
#  keyGenerators:
#    snowflake:
#      type: SNOWFLAKE
#      props:
#        worker-id: 123
```

一切准备就绪后，进入 Sharding 根目录下的 lib 目录运行 start.bat 或是在命令行中：
```
# 使用命令行指定运行端口运行
./start.bat 13306
```
[项目地址](https://github.com/Moby2020/JAVA-000/blob/main/Week_08/demo0802/src/main/java/com/example/demo0802/)
