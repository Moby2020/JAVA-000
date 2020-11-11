### 作业一：使用尽可能多的方式，在一个main函数里面启动一个新线程，运行一个方法并在拿到该方法的返回值后退出主线程

方法一
```
public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 异步执行 下面方法
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        int result = future.get();

//        int result = sum(); //这是得到的返回值


        // 确保  拿到result 并输出
        System.out.println("异步计算结果1为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
        executorService.shutdown();
    }
```


### 作业二：梳理多线程与并发相关的知识
![image](./img/homework2.png)