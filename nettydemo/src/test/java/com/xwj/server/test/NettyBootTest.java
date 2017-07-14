package com.xwj.server.test;

import com.xwj.server.transport.NettyBootServer;

/**
 * Created by James on 2017-07-14.
 */
public class NettyBootTest {

    public static void main(String[] args) {
        NettyBootServer server = new NettyBootServer();
        try {
            server.setPort(9999);
            server.start();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            server._bossGroup().shutdownGracefully();
            server._workGroup().shutdownGracefully();
        }
    }
}
