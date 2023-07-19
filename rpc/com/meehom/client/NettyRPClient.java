package com.meehom.client;

import com.meehom.common.RPCRequest;
import com.meehom.common.RPCResponse;
import com.meehom.register.ServiceRegister;
import com.meehom.register.ZkServiceregister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @version 1.0
 * @Author meehom
 * @Date 2023/7/18 19:05
 */
public class NettyRPClient implements RPCClient{
    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;

    private String host;
    private ServiceRegister serviceRegister;
    private int port;
    public NettyRPClient() {
        this.serviceRegister = new ZkServiceregister();
    }

    static  {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new NettyClientInitializer());
    }
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try {
            InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
            host = address.getHostName();
            port = address.getPort();
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(request);
            channel.closeFuture().sync();
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
