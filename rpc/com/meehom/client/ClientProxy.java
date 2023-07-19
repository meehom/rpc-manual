package com.meehom.client;

import com.meehom.common.RPCRequest;
import com.meehom.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 17:33
 */
@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    private RPCClient rpcClient;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // request 构建
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsTypes(method.getParameterTypes()).build();
        // 数据传输
        RPCResponse response = rpcClient.sendRequest(request);
        return response.getData();
    }

    <T>T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}
