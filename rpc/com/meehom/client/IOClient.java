package com.meehom.client;

import com.meehom.common.RPCRequest;
import com.meehom.common.RPCResponse;
import com.meehom.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 17:41
 */
public class IOClient {
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) {
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // 传给服务器id
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            // 服务器返回
            RPCResponse response = (RPCResponse) objectInputStream.readObject();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
