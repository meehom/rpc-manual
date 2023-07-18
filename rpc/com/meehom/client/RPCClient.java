package com.meehom.client;

import com.meehom.common.Blog;
import com.meehom.common.User;
import com.meehom.service.BlogService;
import com.meehom.service.UserService;

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
            ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
            UserService proxy = clientProxy.getProxy(UserService.class);
            User userByUserId = proxy.getUserByUserId(10);
            System.out.println("服务器返回的user"+ userByUserId);
            User meehom = User.builder().username("meehom").id(100).sex(true).build();
            Integer integer = proxy.insertUserId(meehom);
            System.out.println("向服务端插入数据" + integer);

            BlogService blogService = clientProxy.getProxy(BlogService.class);
            Blog blogById = blogService.getBlogById(100);
            System.out.println("服务器返回的blog"+ blogById);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端启动失败");
        }


    }

}
