package com.meehom.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 18:14
 */
public class SimpleRPCPRCServer implements RPCServer{
    private ServiceProvider serviceProvider;
    public SimpleRPCPRCServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端启动了");
            while(true) {
                Socket socket = serverSocket.accept();
                new Thread(new WorkThread(socket, serviceProvider)).start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void stop() {

    }
}
