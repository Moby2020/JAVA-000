package com.example.demo.client.proxy;

import com.alibaba.fastjson.parser.ParserConfig;
import com.example.demo.client.request.RequestProcess;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcfxJdkInvocationHandler implements InvocationHandler {
    private final Class<?> serviceClass;
    private final String url;
    private RestTemplate restTemplate;

    public <T> RpcfxJdkInvocationHandler(Class<T> serviceClass, String url) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.restTemplate = new RestTemplate();
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return RequestProcess.process(serviceClass, method, args, restTemplate, url);
    }
}
