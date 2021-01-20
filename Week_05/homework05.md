### 单例模式的各种写法，以及它们的优劣

#### 什么是单例模式
	即保证一个类仅创建一个实例，并提供一个全局访问点
	Java 推荐使用静态内部类或者枚举来实现
	[参考连接](https://github.com/lw1243925457/SE-Notes/blob/master/profession/program/java/Java%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F.md)
	

#### 懒汉模式 - 内部枚举实现（推荐）
```Java
public class SingletonLazy {
    private SingletonLazy() {
    }

    // 枚举类是线程安全的，且只会装载一次
    private enum EnumSingleton {
        INSTANCE;

        private SingletonLazy instance;

        EnumSingleton() {
            instance = new SingletonLazy();
        }

        private SingletonLazy getInstance() {
            return instance;
        }
    }

    public static SingletonLazy getInstance() {
        return EnumSingleton.INSTANCE.getInstance();
    }
}
```
	
#### 饿汉模式
```Java
public class SingletonHunger {
    private SingletonHunger() {
    }

    // 线程安全，static 修饰变量，在类初始化过程中能保证唯一执行
    private static SingletonHunger instance = new SingletonHunger();

    public static SingletonHunger getInstance() {
        return instance;
    }
}
```

#### 懒汉模式 - 双重检验
```Java
public class SingletonLazy {
    private SingletonLazy() {
    }

    private static SingletonLazy singleton = null;

    // 线程安全，使用同步锁
    public static SingletonLazy getSingleton() {
        if (singleton == null) {
            synchronized (SingletonLazy.class) {
                if (singleton == null) {
                    singleton = new SingletonLazy();
                }
            }
        }
        return singleton;
    }
}
```

### 使用 Java 的动态代理实现一个简单的 AOP

[作业链接](https://github.com/Moby2020/JAVA-000/blob/main/Week_05/demo/src/test/java/com/example/demo/dynamicProxy/)

### JDBC 接口和数据库连接池

[作业链接](https://github.com/Moby2020/JAVA-000/tree/main/Week_05/demo/src/main/java/com/example/demo/datasource)


