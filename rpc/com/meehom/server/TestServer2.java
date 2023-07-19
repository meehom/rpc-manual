package com.meehom.server;

import com.meehom.service.BlogService;
import com.meehom.service.Impl.BlogServiceImpl;
import com.meehom.service.Impl.UserServiceImpl;
import com.meehom.service.UserService;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 18:45
 */
public class TestServer2 {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8999);
        serviceProvider.providerServiceInterface(userService);
        serviceProvider.providerServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8999);
    }
}
