package com.meehom.register;

import java.net.InetSocketAddress;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/19 14:30
 */
public interface ServiceRegister {
    void register(String serviceName, InetSocketAddress serverAddress);
    InetSocketAddress serviceDiscovery(String serviceName);
}
