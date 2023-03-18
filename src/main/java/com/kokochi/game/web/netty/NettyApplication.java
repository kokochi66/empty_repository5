package com.kokochi.game.web.netty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NettyApplication implements CommandLineRunner {

    private static final int PORT = 1234;

    @Bean
    public NettyServerHandler nettyServerHandler() {
        return new NettyServerHandler();
    }

    @Override
    public void run(String... args) throws Exception {
        SpringApplication.run(NettyApplication.class, args);
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(PORT);
    }
}