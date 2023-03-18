package com.kokochi.game.web.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ChatServerRunner implements CommandLineRunner {

    private final ChatServer chatServer;

    public ChatServerRunner(ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void run(String... args) throws Exception {
        chatServer.run();
    }
}
