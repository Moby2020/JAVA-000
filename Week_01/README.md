学习笔记

## Java字节码
Java字节码是JVM使用的指令集。

#### 字节码分类：
- 栈操作指令，包括与局部变量交互的指令；
 - 程序流程控制指令；
  - 对象操作指令，包括方法调用指令；
   - 算术运算以及类型转换指令。
  
`javap`是 JDK 内置的一款工具，专门用于反编译 class 文件，获取 class 文件中的指令清单。
用法示例：`javap -c -verbose xxx类`

#### 线程栈
JVM是一台基于栈的计算工具，每个线程都有一个独属于自己的线程栈(JVM Stack)用于存储`栈帧(Frame)`。栈帧由`操作数栈`、`局部变量区`和一个`class引用`（指向当前方法在运行时常量池中对应的class）组成。

##### 操作数栈
解释执行过程中，为Java方法分配栈桢时，JVM需要开辟一块额外的空间作为操作数栈，来存放计算的操作数及返回结果。

直接作用于操作数栈的指令：
- dup指令：复制栈顶元素，常用于复制new指令创建的未经初始化的引用；
 - pop指令：常用于舍弃调用指令返回的结果；
  -（dup和pop只能作用于非long、非double类型，因long、double占据两个栈单元）
   - swap指令：交换栈顶两个元素的值。

`dup`共6个指令：`dup`,`dup_x1`,`dup_x2`,`dup2`,`dup2_x1`,`dup2_x2`
- 不带`_x`的指令是复制栈顶数据并压入栈顶，dup、dup2
  - dup复制1个槽位，dup2复制2个槽位
 - 带`_x`的指令是复制栈顶数据并插入栈顶以下的某个位置，将dup和x的系数想家即为需要插入的位置
   - dup_x1插入位置：1+1=2，即栈顶2个Slot下面
    - dup_x2插入位置：1+2=3，即栈顶3个Slot下面
     - dup2_x1插入位置：2+1=3，即栈顶3个Slot下面
      - dup2_x2插入位置：2+2=4，即栈顶4个Slot下面

##### 局部变量区
是Java方法栈桢另一个重要的组成部分，字节码程序可以将计算的结果缓存在这里。
JVM将局部变量区当成一个数组，依次存放：this指针（仅非静态方法）、所传入的参数、以及字节码中局部变量。

#### 方法调用指令
1. invokestatic: 	用于调用静态方法
2. invokespecial:	用于调用私有实例方法、构造器；使用super关键字调用父类实例方法、构造器；所有实现接口的默认方法；
3. invokevirtual:		用于调用非私有实例方法
4. invokeinterface:	用于调用接口方法
5. invokedynamic:	用于调用动态方法

## 类加载器

#### 类的生命周期和加载过程
生命周期：`加载Loading`、`校验Verification`、`准备Preparation`、`解析Resolution`、`初始化initialization`、`使用Using`、`卸载Unloading`，前 5 个步骤是加载过程。

`加载Loading`
- 是指查找字节流，创建类的过程
 - Java分为基本类型和引用类型，其中，引用类型又分为：类、接口、数组类。基本类型由JVM事先定义好，数组类由JVM生成，另外两种则有对应的字节流。
  - 字节流常见形式为class文件，若找不到class文件，则报错`NoClassDefFoundError`

`校验Verification`
- 保证被加载的类能够满足JVM的约束条件

`准备Preparation`
- 为被加载类的静态字段分配内存（和构造方法表）

`解析Resolution`
- 将`符号引用`解析为实际引用
 - 符号引用，用来指代所要调用的方法，包含目标方法所在类的名字、目标方法的名字、接收参数类型以及返回值类型

`初始化initialization`
- JVM规范明确规定，必须在类的首次“主动使用”时才执行类的初始化
 - 初始化的过程执行：类的构造器、static静态变量赋值语句、static静态代码块
 
##### 类的初始化触发条件
1. JVM启动时初始化用户指定的主类
2. 遇到新建目标类的`new`指令时，初始化new指令的目标类
3. 遇到调用静态方法的指令，初始化该静态方法所在的类；
4. 遇到调用静态字段的指令，初始化该静态字段所在的类；
5. 子类的初始化会触发父类的初始化；
6. 一个定义了`default`方法的接口，初始化直接实现或间接实现该接口的类时，也会触发该接口的初始化；
7. 使用反射API对某个类进行反射调用时，初始化该类；
8. 初次调用MethodHandle实例时，初始化该MethodHandle指向的方法所在的类。

##### 不触发类初始化的情况
1. 通过子类引用父类静态字段，只会初始化父类，而不初始化子类；
2. 定义对象数组，不会触发该对象初始化(private Student student[]);
3. 常量在编译期间会存入调用类的常量池，本质上没有直接引用定义常量的类，不触发定义常量所在的类；
4. 通过类名获取Class对象；
5. 通过Class.forName加载指定类，若指定参数initialize为false时；
6. 通过ClassLoader默认的loadClass方法

#### 类加载器实例
类的加载过程可以描述为：通过一个类的全限定名来捕获此类的class对象

系统自带的类加载器由有
- 启动类加载器(bootstrap class loader)：加载Java核心类，由C++实现，负责加载`/jre/lib/rt.jar`中的class
 - 扩展类加载器(extensions class loader)：负责加载扩展目录`/jre/lib/ext/`下的class，或者是由`java.ext.dirs`系统属性指定目录中的JAR包
  - 应用类加载器(app class loader)：加载应用程序路径下的类

类加载器的三个特点
- 双亲委派机制：即接收到加载请求时，会先将请求转发给父类、祖先类加载器；（若所有的类加载器都没有找到指定名称的类，则报错`ClassNotFoundException`)
 - 负责依赖：类加载器加载某个类时，若发现该类依赖于另外几个类或接口，也会尝试加载这些依赖项；
  - 缓存加载：某个类被一个类加载器加载后，会缓存该加载结果。

一些实用技巧：
1. 如何排查找不到Jar包的问题？
2. 如何排查类的方法不一致的问题?
3. 查看加载了哪些类，以及加载顺序：`在Java启动命令行参数上添加「-XX:+TraceClassLoading」如：$ java -XX:+TraceClassLoading 对象名`
4. 怎么调整或修改ext和本地加载路径?
5. 怎么运行期加载额外的jar包或者class呢？

## JVM内存结构
JVM内部使用的Java内存模型，逻辑上划分为`线程栈`和`堆内存`两部分：

![image](./noteImg/JVM.png)

JVM中，每个正在运行的线程都有自己的线程栈
- 每个线程都只能访问自己的线程栈，且不能访问(看不见)其他线程的局部变量
 - 原生类型的局部变量都存储在线程栈中
  - 对象引用为局部变量是，线程栈中保存的是该对象的引用地址，实际对象内容保存在栈中
   - 成员变量都保存在堆中，不管是原始类型还是对象引用

堆内存又称为“`共享堆`，堆中的所有对象，可以被所有线程访问

栈内存结构

![image](./noteImg/stack.png)

每启动一个线程，JVM就会在栈空间分配对应的线程栈，如1MB（-Xss1m）

堆内存结构

![image](./noteImg/Heap.png)

堆内存是所有线程共用的内存空间，理论上大家都可以访问里面的内容。

JVM参数
1. `-Xmx`: 最大堆内存（不要超过机器内存的70%）
2. `-Xms`: 堆内存初始化大小，与-Xmx大小一致，两者不一致时，堆内存扩容可能会导致性能抖动
3. `-Xmn`: 设置新生代大小，建议为-Xmx的1/2～1/4，等价于-XX:NewSize
4. `-Xss`: 设置每个线程栈字节数，如「-Xss 1m」指定线程栈为1MB
5. `-XX:MaxMetaspeaceSize=size`，Java8默认不限制meta空间，不允许设置
6. `-XX:MaxDirectMemorySize=size`，系统可用最大堆外内存

#### Java内存模型与 as-if-serial
`as-if-serial`语义是指：不管怎样重排序（编译器和处理器为了提高并行度），单线程程序的执行结果不能被改变。因此编译器和处理器不会对存在依赖关系的操作做重排序。

#### Java内存模型与 happens-before 关系
`happens-before`是Java内存模型中一个重要概念，`happens-before`用来描述两个操作的内存可见性
- 如果操作X `happens-before` 操作Y，那么 X 的结果对于 Y 可见
 - 此外`happens-before`还具有传递性。若操作X `happens-before` 操作Y，操作Y `happens-before` 操作Z，则操作X `happens-before` 操作Z

Java内存模型定义了下述线程间`happens-before`关系：
1. 同一个线程中，靠前的字节码`happens-before`靠后的字节码（这并不意味着前者一定比后者先执行，若后者没有数据依赖于前者，它们可能会被重排序）
2. 解锁操作`happens-before`对同一把锁的加锁操作
3. `volatile`字段的写操作`happens-before`对同一字段的读操作（是指在两个不同的线程中）
4. 线程启动操作(Thread.start())`happens-before`该线程第一个操作
5. 线程的最后一个操作 `happens-before` 它的终止事件（即其他线程通过 Thread.isAlive() 或 Thread.join() 判断该线程是否中止）
6. 构造器中的最后一个操作 `happens-before` 析构器的第一个操作

#### Java内存模型底层实现
Java 内存模型是通过`内存屏障(memory barrier)`来禁止重排序。即时编译器会针对前面提到的每一个 `happens-before` 关系，向正在编译的目标方法中插入相应的读读、读写、写读以及写写内存屏障。其中只有写读内存屏障会被替换成具体 CPU 指令，其余为空操作。
- 例如，对于 `volatile` 字段，即时编译器将在 `volatile` 字段的读写操作前后各插入一些内存屏障。然而，在 X86_64 架构上，只有 `volatile` 字段写操作之后的写读内存屏障需要用具体指令来替代。该具体指令的效果，可以简单理解为强制刷新处理器的写缓存。
 - 强制刷新写缓存，将使得当前线程写入 `volatile` 字段的值（以及写缓存中已有的其他内存修改），同步至主内存之中。
  - 由于内存写操作会同时无效化其他处理器所持有的、指向同一地址的缓存行，因此可以认为其他处理器可以立即看到`volatile`字段的最新值。
  




