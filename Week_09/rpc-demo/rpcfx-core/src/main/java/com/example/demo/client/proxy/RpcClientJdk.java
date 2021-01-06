package com.example.demo.client.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

@Slf4j
public class RpcClientJdk implements RpcClient {

    @Override
    public <T> T create(Class<T> serviceClass, String url) {
        ClassLoader classLoader = RpcClientJdk.class.getClassLoader();
        Class[] classes = new Class[]{serviceClass};
        log.info("client jdk proxy instance create and return");
        return (T) Proxy.newProxyInstance(classLoader, classes, new RpcfxJdkInvocationHandler(serviceClass, url));
    }
}
