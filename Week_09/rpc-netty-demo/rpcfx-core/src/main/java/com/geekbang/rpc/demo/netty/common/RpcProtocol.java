package com.geekbang.rpc.demo.netty.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RpcProtocol {
    /**
     * 数据大小
     */
    private int len;

    /**
     * 数据内容
     */
    private byte[] content;
}
// update ch_admin