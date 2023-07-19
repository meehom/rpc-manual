package com.meehom.client;

import com.meehom.common.RPCRequest;
import com.meehom.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 19:00
 */
@AllArgsConstructor
public class SimpleRPCClient  implements  RPCClient{
    private String host;
    private int port;
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            RPCResponse response = (RPCResponse) objectInputStream.readObject();
            return response;
        } catch (Exception e) {
            return null;
        }


    }
}
