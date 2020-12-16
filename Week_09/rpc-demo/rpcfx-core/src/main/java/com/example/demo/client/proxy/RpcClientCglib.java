package com.example.demo.client.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class RpcClientCglib implements RpcClient {
    @Override
    public <T> T create(Class<T> serviceClass, String url) {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new RpcfxCglibInvocationHandler(serviceClass, url));
        enhancer.setSuperclass(serviceClass);
        log.info("client cglib proxy instance create and return");
        return (T) enhancer.create();
    }
}
