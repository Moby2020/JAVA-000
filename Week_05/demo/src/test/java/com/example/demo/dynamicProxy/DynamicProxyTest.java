package com.example.demo.dynamicProxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    @Test
    public void test() {
        DynamicProxyService service = new DynamicProxyServiceImpl();
        ClassLoader classLoader = service.getClass().getClassLoader();
        Class<?>[] interfaces = service.getClass().getInterfaces();
        ProxyHandler handler = new ProxyHandler(service);

        DynamicProxyService proxyInstance = (DynamicProxyService) Proxy.newProxyInstance(classLoader, interfaces, handler);
        proxyInstance.print();
    }
}
