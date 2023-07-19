package com.meehom.loadbalance;

import java.util.List;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/19 17:18
 */
public interface LoadBalance {
    String banlance(List<String> addressList);
}
