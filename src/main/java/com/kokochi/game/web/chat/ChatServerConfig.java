package com.kokochi.game.web.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatServerConfig {

    @Bean
    public ChatServer chatServer() {
        return new ChatServer();
    }
}
