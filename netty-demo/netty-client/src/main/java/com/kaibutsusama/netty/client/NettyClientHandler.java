package com.kaibutsusama.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/12 9:21
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    public static Channel serverChannel = null;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        serverChannel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client--收到消息：" + msg);
    }

    // 出现异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("client 读取数据出现异常");
        cause.printStackTrace();
        ctx.close();
    }

}
