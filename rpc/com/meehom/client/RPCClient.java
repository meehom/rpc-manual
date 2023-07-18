package com.meehom.client;

import com.meehom.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * @Author meehom
 * @Date 2023/7/18 14:02
 * @version 1.0
 */
public class RPCClient {
    public static void main(String[] args) {
        // 建立socket连接
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8899);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // 传给服务器id
            objectOutputStream.writeInt(new Random().nextInt());
            objectOutputStream.flush();
            // 服务器返回
            User user = (User) objectInputStream.readObject();
            System.out.println("服务器返回的user"+ user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端启动失败");
        }


    }

}
