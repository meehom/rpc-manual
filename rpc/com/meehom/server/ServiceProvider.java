package com.meehom.server;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 18:10
 */

import com.meehom.register.ServiceRegister;
import com.meehom.register.ZkServiceregister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;


/**
 * 存放服务接口名和服务端对应实现类，服务启动时要暴露实现类
 */
public class ServiceProvider {
    private Map<String, Object> interfaceProvider;
    private ServiceRegister serviceRegister;
    private String host;
    private int port;

    public ServiceProvider(String host, int port){
        this.host = host;
        this.port = port;
        this.interfaceProvider = new HashMap<>();
        this.serviceRegister = new ZkServiceregister();
    }

    public void providerServiceInterface(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class clazz : interfaces) {
            interfaceProvider.put(clazz.getName(), service);
            serviceRegister.register(clazz.getName(), new InetSocketAddress(host, port));
        }
    }

    public Object getService(String interfaceName) {
        return interfaceProvider.get(interfaceName);
    }
}
