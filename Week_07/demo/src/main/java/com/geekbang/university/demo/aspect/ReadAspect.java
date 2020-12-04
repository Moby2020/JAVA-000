package com.geekbang.university.demo.aspect;

import com.geekbang.university.demo.config.ManagementCenter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ReadAspect {

    @Autowired
    ManagementCenter managementCenter;

    @Pointcut("@annotation(com.geekbang.university.demo.annotation.DataSourceSelector)")
    public void read() {
    }

    @Around("read()")
    public List<Map<String, Object>> setDatabaseSource(ProceedingJoinPoint point) throws Throwable {
        log.info("data source change......");
        Object[] args = point.getArgs();
        args[0] = managementCenter.getSlaveDataSource();
        return (List<Map<String, Object>>) point.proceed(args);
    }
}
