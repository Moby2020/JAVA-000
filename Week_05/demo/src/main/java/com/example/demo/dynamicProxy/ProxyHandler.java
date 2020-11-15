package com.example.demo.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    private DynamicProxyService dynamicProxyService;

    public ProxyHandler(DynamicProxyService dynamicProxyService) {
        this.dynamicProxyService = dynamicProxyService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(dynamicProxyService, args);
        after();
        return null;
    }

    private void before() {
        System.out.println(">>> before DynamicProxyServiceImpl...");
    }

    private void after() {
        System.out.println("<<< after DynamicProxyServiceImpl...");
    }
}
