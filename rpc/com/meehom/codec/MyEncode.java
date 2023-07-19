package com.meehom.codec;

import com.meehom.common.RPCRequest;
import com.meehom.common.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/19 12:33
 */
@AllArgsConstructor
public class MyEncode extends MessageToByteEncoder {
    private Serializer serializer;
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf out) throws Exception {

        // 写入消息类型
        if (msg instanceof RPCRequest) {
            out.writeShort(MessageType.REQUEST.getCode());
        }else if(msg instanceof RPCResponse) {
            out.writeShort(MessageType.RESPONSE.getCode());
        }
        // 写入序列化方式
        out.writeShort(serializer.getType());
//        System.out.println("type:" + serializer.getType());
        // 得到序列化数组
        byte[] serialize = serializer.serialize(msg);
        // 写入长度
        out.writeInt(serialize.length);
        // 写入序列化数组
        out.writeBytes(serialize);
    }
}
