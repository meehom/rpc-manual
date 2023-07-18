package com.meehom.service;

import com.meehom.common.User;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 14:10
 */
public interface UserService {
    User getUserByUserId(Integer id);

    // 给这个服务增加一个功能
    Integer insertUserId(User user);
}
