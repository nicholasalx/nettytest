package com.xwj.server.transport;


import com.google.common.collect.Maps;
import com.xwj.server.codec.FBoxMessage;
import com.xwj.server.codec.FBoxMessageDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by wanghui on 4/9/16.
 */
@Slf4j
public class NettyBootServer {
    public static final short VERSION = 4;

    private int port;

    //@PostConstruct
    public void start() {
        log.info("Socket[port:{}] Server starting........", port);

        this._start();

        log.info("Socket Server started!");
    }

    private void _start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap
                .group(this._bossGroup(), this._workGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        super.channelRegistered(ctx);
                    }
                })
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new FBoxMessageDecoder(1024, 16, 4))
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        FBoxMessage fmsg = (FBoxMessage) msg;
                                        String remoteAddress = ch.remoteAddress().getAddress().getHostAddress();
                                        Integer remotePort = ch.remoteAddress().getPort();


                                        ByteBuf b = ctx.alloc().buffer();
                                        ctx.channel().writeAndFlush(b);
                                    }

                                    @Override
                                    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                                        super.channelUnregistered(ctx);

                                    }
                                });
                    }
                });
        try {
            bootstrap.bind(port).sync();
            log.info("Listen port [{}] successed.", port);
        } catch (InterruptedException e) {
            log.error("Netty服务异常.", e);
        }

    }


    public NioEventLoopGroup _bossGroup() {
        return new NioEventLoopGroup();
    }

    public NioEventLoopGroup _workGroup() {
        return new NioEventLoopGroup();
    }

    public void setPort(int port) {
        this.port = port;
    }
}
