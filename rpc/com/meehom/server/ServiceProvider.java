package com.meehom.server;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 18:10
 */

import java.util.HashMap;
import java.util.Map;


/**
 * 存放服务接口名和服务端对应实现类，服务启动时要暴露实现类
 */
public class ServiceProvider {
    private Map<String, Object> interfaceProvider;

    public ServiceProvider(){
        this.interfaceProvider = new HashMap<>();
    }

    public void providerServiceInterface(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class clazz : interfaces) {
            interfaceProvider.put(clazz.getName(), service);
        }
    }

    public Object getService(String interfaceName) {
        return interfaceProvider.get(interfaceName);
    }
}
