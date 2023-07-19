package com.meehom.loadbalance;

import java.util.List;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/19 17:21
 */
public class RoundLoadBalance implements LoadBalance{
    private int choose = -1;
    @Override
    public String banlance(List<String> addressList) {
        choose ++;
        choose = choose % addressList.size();
        return addressList.get(choose);
    }
}
