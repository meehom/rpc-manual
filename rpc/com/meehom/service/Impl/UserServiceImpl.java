package com.meehom.service.Impl;

import com.meehom.common.User;
import com.meehom.service.UserService;

import java.util.Random;
import java.util.UUID;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 14:11
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUserByUserId(Integer id) {
        Random random = new Random();
        User user = User.builder().username(UUID.randomUUID().toString()).id(id).sex(random.nextBoolean()).build();
        return user;
    }

    @Override
    public Integer insertUserId(User user) {
        System.out.println("插入数据成功："+user);
        return 1;
    }
}
