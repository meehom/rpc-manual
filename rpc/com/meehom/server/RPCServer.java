package com.meehom.server;

import com.meehom.common.User;
import com.meehom.service.Impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                        Integer id = objectInputStream.readInt();
                        User userByUserId = userService.getUserByUserId(id);
                        objectOutputStream.writeObject(userByUserId);
                        objectOutputStream.flush();
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
