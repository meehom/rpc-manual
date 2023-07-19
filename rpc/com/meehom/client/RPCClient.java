package com.meehom.client;

import com.meehom.common.Blog;
import com.meehom.common.RPCRequest;
import com.meehom.common.RPCResponse;
import com.meehom.common.User;
import com.meehom.service.BlogService;
import com.meehom.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * @Author meehom
 * @Date 2023/7/18 14:02
 * @version 1.0
 */
public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
