package com.meehom.register;


import com.meehom.loadbalance.LoadBalance;
import com.meehom.loadbalance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/19 14:31
 */
public class ZkServiceregister implements ServiceRegister {

    private CuratorFramework client;

    private LoadBalance loadBalance = new RandomLoadBalance();

    private static final String ROOT_PATH = "MyRPC";

    public ZkServiceregister() {
        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(1000, 3);
        this.client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(40000).retryPolicy(policy).namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("zookeeper连接成功");
    }

    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try {
            if (client.checkExists().forPath("/" + serviceName) == null) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName);
            }
            String path = "/" + serviceName + "/" + getServiceAddress(serverAddress);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 地址 -> XXX.XXX.XXX.XXX:port 字符串
    private String getServiceAddress(InetSocketAddress serverAddress) {
        return serverAddress.getHostName() +
                ":" +
                serverAddress.getPort();
    }

    private InetSocketAddress parseAddress(String address) {
        String[] result = address.split(":");
        return new InetSocketAddress(result[0], Integer.parseInt(result[1]));
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        List<String> strings = null;
        try {
            strings = client.getChildren().forPath("/" + serviceName);
//            String string = strings.get(0);
            String string = loadBalance.banlance(strings);
            return parseAddress(string);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
