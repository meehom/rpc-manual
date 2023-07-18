package com.meehom.server;

import com.meehom.common.RPCRequest;
import com.meehom.common.RPCResponse;
import com.meehom.common.User;
import com.meehom.service.Impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 14:10
 */
public class RPCServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        try {
            ServerSocket serverSocket = new ServerSocket(8899);
            while (true) {
                Socket accept = serverSocket.accept();
                new Thread(() -> {
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
                        try {
                            RPCRequest rpcRequest = (RPCRequest) objectInputStream.readObject();
                            Method method = userService.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamsTypes());
                            Object invoke = method.invoke(userService, rpcRequest.getParams());
                            objectOutputStream.writeObject(RPCResponse.success(invoke));
                            objectOutputStream.flush();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }


    }
}
