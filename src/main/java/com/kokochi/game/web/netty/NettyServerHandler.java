package com.kokochi.game.web.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@ChannelHandler.Sharable
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 클라이언트로부터 메세지를 읽어들임
        ByteBuf in = (ByteBuf) msg;
        try {
            // Do something with the received data and write a response.
            String message = in.toString(io.netty.util.CharsetUtil.US_ASCII);
            System.out.println("Received: " + message);
            String response = "Server response: " + message;
            ctx.write(Unpooled.copiedBuffer(response.getBytes()));
        } finally {
            in.release();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 클라이언트가 연결되었을 때 호출
        log.info("Client Channel is Active");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 클라이언트가 연결을 끊었을 때 호출
        log.info("Client Channel is InActive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}