package com.kokochi.game.web.chat;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessions = new ArrayList<>();
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        for(WebSocketSession s : sessions) {
            s.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
}