package com.meehom.client;

import com.meehom.common.User;
import com.meehom.service.UserService;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 19:03
 */
public class TestClient {
    public static void main(String[] args) {
        NettyRPClient simpleRPCClient = new NettyRPClient();
        ClientProxy clientProxy = new ClientProxy(simpleRPCClient);
        UserService proxy = clientProxy.getProxy(UserService.class);
        User userByUserId = proxy.getUserByUserId(20);
        System.out.println("从服务端返回数据" + userByUserId);
    }
}
