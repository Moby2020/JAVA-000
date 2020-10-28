### 01 使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例
平均每次生成对象数量：
| GC/最大堆内存 | 512M | 1024M | 2048M |
| ------ | ------ |------ |------ |
| SerialGC | 10599 | 11653 | 12700 |
| ParallelGC | 8437 | 12696 | 13735 |
| CMS GC | 10511 | 13229 | 12785 |
| G1 GC | 10463 | 12974 | 10261 |

GC发生次数以及系统暂停时间：
| GC/最大堆内存 | 512M | 1024M | 2048M |
| ------ | ------ |------ |------ |
| SerialGC | 7次Young GC平均每次耗时0.03秒;9次Full GC平均每次耗时0.04秒 | 8次Young GC，平均每次程耗时0.05秒;1次Full GC耗时0.08秒 | 4次Young GC,平均每次程耗时0.08秒 |
| ParallelGC | 26次Young GC,平均每次耗时0.02秒；11次Full GC平均每次耗时0.03秒 | 20次Young GC,平均每次耗时0.02秒；1次Full GC耗时0.05秒 | 7次Young GC,平均每次耗时0.02秒； |
| CMS GC | 12次GC,平均每次耗时0.02秒;5次Full GC，平均每次耗时0.05秒 | 10次GC,平均每次耗时0.04秒;1次Full GC耗时0.06秒 | 6次GC,平均每次耗时0.06秒 |
| G1 GC | / | 4次[GC cleanup 506M->505M(1024M), 0.0007034 secs] | / |

### 02 使用压测工具(wrk或sb)，演练gateway-server-0.0.1-SNAPSHOT.jar 示例
使用命令示例：`wrk -t 16 -c 400 -d 30s http://localhost:8088/api/hello`

平均每秒处理完成请求个数：
| GC/最大堆内存 | 512M | 1024M | 2048M |
| ------ | ------ |------ |------ |
| SerialGC | 36809.88 | 50930.29 | 51509.89 |
| ParallelGC | 26137.95 | 50930.29 | 53314.46 |
| CMS GC | 38546.85 | 49560.19 | 51589.89 |
| G1 GC | 33695.94 | 50237.81 | 48396.02 |

平均每秒读取数据：
| GC/最大堆内存 | 512M | 1024M | 2048M |
| ------ | ------ |------ |------ |
| SerialGC | 4.39MB | 5.22MB | 6.15MB |
| ParallelGC | 3.12MB | 6.08MB | 6.37MB |
| CMS GC | 4.60MB | 5.92MB | 6.16MB |
| G1 GC | 4.02MB | 6.00MB | 5.78MB |
 
 如上表所示：GC堆内存相同时，各个版本的 GC 压测结构都差不多。
 
### 03 写一段代码，使用HttpClient或OkHttp访问 http://localhost:8803，代码提交到 github。
添加 Maven 依赖
```
<dependency>
	<groupId>org.apache.httpcomponents</groupId>
	<artifactId>httpclient</artifactId>
	<version>4.3.5</version>
</dependency>

<dependency>
	<groupId>org.apache.commons</groupId>
	<artifactId>commons-io</artifactId>
	<version>1.3.2</version>
</dependency>
```

Code:
![image](./imgs/homework_code.png)

result:
![image](./imgs/homework_result.png)

