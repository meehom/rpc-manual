package com.meehom.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/19 17:19
 */
public class RandomLoadBalance implements LoadBalance{
    @Override
    public String banlance(List<String> addressList) {
        Random random = new Random();
        int choose = random.nextInt(addressList.size());
        System.out.println("负载均衡选择了" + choose + "服务器");
        return addressList.get(choose);
    }
}
