package com.example.demo1.common;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSourceSelector {
    DynamicDataSourceEnum value() default DynamicDataSourceEnum.MASTER;

    boolean clear() default true;
}
