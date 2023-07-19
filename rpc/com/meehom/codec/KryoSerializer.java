package com.meehom.codec;

import com.alibaba.fastjson.JSONObject;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.meehom.common.RPCRequest;
import com.meehom.common.RPCResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/19 13:40
 */
public class KryoSerializer implements Serializer {

    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Output output = new Output(baos)) {
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output, obj);
            output.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Kryo serialization failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
//        System.out.println("bbb");
        switch (messageType) {
            case 0:
                RPCRequest request = deserializeRequest(bytes);
                if (request.getParams() != null) {
                    Object[] objects = new Object[request.getParams().length];
                    // 把字节数组反序列化成对应的对象
                    for (int i = 0; i < objects.length; i++) {
                        Class<?> paramsType = request.getParamsTypes()[i];
                        if (!paramsType.isAssignableFrom(request.getParams()[i].getClass())){
                            Object param = request.getParams()[i];
                            objects[i] = kryoDeserialize((byte[]) param, paramsType);
                        }else{
                            objects[i] = request.getParams()[i];
                        }
                    }
                    request.setParams(objects);
                }
                return request;
            case 1:
                RPCResponse response = deserializeResponse(bytes);

                if (response.getData() != null) {
                    Class<?> dataType = response.getDataType();
                }
                return response;
            default:
                throw new IllegalArgumentException("Invalid message type: " + messageType);
        }
    }

    @Override
    public int getType() {
        return 2;
    }

    private RPCRequest deserializeRequest(byte[] bytes) {
        Kryo kryo = kryoThreadLocal.get();
        Input input = new Input(bytes);
        return kryo.readObject(input, RPCRequest.class);
    }

    private RPCResponse deserializeResponse(byte[] bytes) {
        Kryo kryo = kryoThreadLocal.get();
        Input input = new Input(bytes);
        return kryo.readObject(input, RPCResponse.class);
    }

    private Object kryoDeserialize(byte[] bytes, Class<?> clazz) {
        Kryo kryo = kryoThreadLocal.get();
        Input input = new Input(bytes);
        return kryo.readObject(input, clazz);
    }
}