package com.meehom.server;


/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 14:10
 */
public interface RPCServer {
    void start(int port);
    void stop();
}
