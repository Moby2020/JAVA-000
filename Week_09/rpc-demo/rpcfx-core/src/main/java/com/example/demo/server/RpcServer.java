package com.example.demo.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.api.RpcfxRequest;
import com.example.demo.api.RpcfxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class RpcServer {
    private ApplicationContext applicationContext;

    public RpcServer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();
        // 作业1：改成泛型和反射
        Object service = applicationContext.getBean(serviceClass);

        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams());
            log.info("Server method invoke result: " + result.toString());
            // 两次json序列化能否合并成一个
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            log.info("Server Response serialize to string return");
            return response;
        } catch (IllegalAccessException | InvocationTargetException e) {
            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> aClass, String methodName) {
        return Arrays.stream(aClass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }
}
