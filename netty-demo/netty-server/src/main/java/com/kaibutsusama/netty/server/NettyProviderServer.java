package com.kaibutsusama.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/12 8:59
 */
public class NettyProviderServer {

    /**
     * 服务端口
     */
    private int port;

    public NettyProviderServer(int port) {
        this.port = port;
    }

    public void runServer() throws Exception {
        //1.定义时间的BOSS监听组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        //2.定义Worker，用于处理被接收的连接
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // 定义NIO服务启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 配置NIO服务的启动相关参数
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    // tcp 连接最大个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 保持连接的正常状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 重写initChannel初始化方法
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 管道的注册handler
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 编码通道处理
                            pipeline.addLast("decode", new StringDecoder());
                            // 转码的通道处理
                            pipeline.addLast("encode", new StringEncoder());
                            // 处理接收到的请求数据
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            System.err.println("-------server 启动------");

            // 监听输入端消息并发送给所有客户端
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                            String str = in.readLine();
                            if (NettyServerHandler.channelList.size() > 0) {
                                for (Channel channel : NettyServerHandler.channelList) {
                                    channel.writeAndFlush(str);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            // 5. 绑定端口，启动服务， 开始接受客户端链接
            ChannelFuture cf = serverBootstrap.bind(port).sync();
            cf.channel().closeFuture().sync();

        } finally {
            // 实现Netty的优雅关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    // 开启netty服务线程
    public static void main(String[] args) throws Exception {
        new NettyProviderServer(9911).runServer();
    }
}
