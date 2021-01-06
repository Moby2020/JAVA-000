package com.example.demo.client.proxy;

import com.alibaba.fastjson.parser.ParserConfig;
import com.example.demo.client.request.RequestProcess;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;

public class RpcfxCglibInvocationHandler implements MethodInterceptor {
    private final Class<?> serviceClass;
    private final String url;
    private RestTemplate restTemplate;

    <T> RpcfxCglibInvocationHandler(Class<T> serviceClass, String url) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.restTemplate = new RestTemplate();
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return RequestProcess.process(serviceClass, method, objects, restTemplate, url);
    }
}
