package com.meehom.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/19 12:39
 */
public class MyDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 1. 读取消息类型
        short messageType = in.readShort();
//        System.out.println(messageType);
        if (messageType != MessageType.REQUEST.getCode() && messageType != MessageType.RESPONSE.getCode()) {
            System.out.println("暂时不支持此数据");
            return;
        }
        // 2. 读取序列化类型
        short serilizerType = in.readShort();
//        System.out.println(serilizerType);
        Serializer serializerByCode = Serializer.getSerializerByCode(serilizerType);
//        System.out.println(serializerByCode.getClass());
        if (serializerByCode == null) throw new RuntimeException("不存在对应的序列化器");
        // 3. 读取数据序列化之后的字节长度
        int length = in.readInt();
        // 4. 读取序列化数组
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        // 5. 用对应解码器解码
        Object deserialize = serializerByCode.deserialize(bytes, messageType);
//        System.out.println(deserialize);
        out.add(deserialize);
    }
}
