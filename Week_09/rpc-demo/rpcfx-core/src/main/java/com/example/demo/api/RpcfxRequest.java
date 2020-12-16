package com.example.demo.api;

import lombok.Data;

@Data
public class RpcfxRequest {
    // 接口类名称
    private String serviceClass;
    // 方法名
    private String method;
    // 参数
    private Object[] params;
}
