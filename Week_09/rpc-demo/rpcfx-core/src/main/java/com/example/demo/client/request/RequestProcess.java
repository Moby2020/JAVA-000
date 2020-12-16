package com.example.demo.client.request;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.RpcfxRequest;
import com.example.demo.api.RpcfxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;

@Slf4j
public class RequestProcess {
    public static Object process(Class<?> service, Method method, Object[] params, RestTemplate restTemplate, String url) {
        log.info("Client proxy instance method invoke");

        log.info("Build Rpc request");
        RpcfxRequest rpcfxRequest = new RpcfxRequest();
        rpcfxRequest.setServiceClass(service.getName());
        rpcfxRequest.setMethod(method.getName());
        rpcfxRequest.setParams(params);

        log.info("Client send request to Server");
        HttpEntity<RpcfxRequest> request = new HttpEntity<>(rpcfxRequest);
        ResponseEntity<RpcfxResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, RpcfxResponse.class);
        RpcfxResponse rpcfxResponse = response.getBody();
        log.info("Client receive response Object");

        assert rpcfxResponse != null;
        if (!rpcfxResponse.getStatus()) {
            log.info("Client receive exception");
            rpcfxResponse.getException().printStackTrace();
            return null;
        }
        log.info("Response:: " + rpcfxResponse.getResult());
        return JSON.parse(rpcfxResponse.getResult().toString());
    }
}
