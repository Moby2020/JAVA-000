package com.example.demo1.aop;

import com.example.demo1.common.DataSourceContextHolder;
import com.example.demo1.common.DataSourceSelector;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Order(value = 1)
@Component
public class DataSourceContextAop {

    @Around("@annotation(com.example.demo1.common.DataSourceSelector)")
    public Object setDynamicDataSource(ProceedingJoinPoint pjp) throws Throwable {
        boolean clear = true;
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();
            DataSourceSelector dataSourceImport = method.getAnnotation(DataSourceSelector.class);
            clear = dataSourceImport.clear();
            DataSourceContextHolder.set(dataSourceImport.value().getDataSourceName());
            log.info("========change datasource to：{}", dataSourceImport.value().getDataSourceName());
            return pjp.proceed();
        } finally {
            if (clear) {
                DataSourceContextHolder.clear();
            }
        }
    }
}
