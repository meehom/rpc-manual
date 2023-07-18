package com.meehom.server;

import com.meehom.common.RPCRequest;
import com.meehom.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 18:17
 */
@AllArgsConstructor
public class WorkThread implements Runnable{
    private Socket socket;

    private  ServiceProvider serviceProvider;
    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            RPCRequest rpcRequest = (RPCRequest) objectInputStream.readObject();
            RPCResponse response = getResponse(rpcRequest);
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private RPCResponse getResponse(RPCRequest request) {
        String interfaceName = request.getInterfaceName();
        Object service = serviceProvider.getService(interfaceName);
        Method method = null;
        try {
            method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
