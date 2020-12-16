package com.geekbang.rpc.demo.netty.client;

import com.alibaba.fastjson.JSON;
import com.geekbang.rpc.demo.api.RpcResponse;
import com.geekbang.rpc.demo.netty.common.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * 这里使用并发的等待-通知机制来拿到结果
 */
@Slf4j
public class RpcClientSyncHandler extends SimpleChannelInboundHandler<RpcProtocol> {
    private CountDownLatch countDownLatch;
    private RpcResponse response;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol msg) throws Exception {
        log.info("Netty client receive message:");
        log.info("Message length: " + msg.getLen());
        log.info("Message content: " + new String(msg.getContent(), CharsetUtil.UTF_8));

        // 将 RpcResponse 字符串 反序列化成 RpcResponse 对象
        RpcResponse rpcResponse = JSON.parseObject(new String(msg.getContent(), CharsetUtil.UTF_8), RpcResponse.class);
        log.info("Netty client serializer : " + rpcResponse.toString());
        response = rpcResponse;
        countDownLatch.countDown();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 锁的初始化
     */
    void setLatch(CountDownLatch latch) {
        this.countDownLatch=latch;
    }

    /**
     * 阻塞等待结果后返回
     * @return 后台服务器响应
     * @throws InterruptedException
     */
    RpcResponse getResponse() throws InterruptedException {
        countDownLatch.await();
        return response;
    }
}
