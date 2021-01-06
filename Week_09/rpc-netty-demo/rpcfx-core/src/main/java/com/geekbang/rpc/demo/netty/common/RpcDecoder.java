package com.geekbang.rpc.demo.netty.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Rpc framework 自定义解码器
 * bytes -> rpcProtocol
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {
    private int length = 0;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        log.info("Netty decode run");
        if (in.readableBytes() >= 4) {
            if (length == 0) {
                length = in.readInt();
            }
            if (in.readableBytes() < length) {
                log.info("Readable data is less, wait");
                return;
            }
            byte[] content = new byte[length];
            if (in.readableBytes() >= length) {
                in.readBytes(content);
                RpcProtocol protocol = new RpcProtocol();
                protocol.setLen(length);
                protocol.setContent(content);
                out.add(protocol);
            }
            length = 0;
        }
    }
}
