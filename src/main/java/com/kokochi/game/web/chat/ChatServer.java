package com.kokochi.game.web.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatServer implements Runnable {
    private ServerSocket serverSocket;
    private List<ChatServerThread> threads = new ArrayList<ChatServerThread>();

    public ChatServer() {
        try {
            this.serverSocket = new ServerSocket(1234);
        } catch (Exception e) {
            System.out.println("ChatServer 생성자 에러 : " + e);
        }
    }

    public void run() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();

                ChatServerThread thread = new ChatServerThread(socket, this);
                threads.add(thread);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println("ChatServer run 에러 : " + e);
        }
    }

    public void removeThread(ChatServerThread thread) {
        threads.remove(thread);
    }

    public void broadcast(String message, ChatServerThread sender) {
        for (ChatServerThread thread : threads) {
            if (thread != sender) {
                thread.sendMessage(message);
            }
        }
    }

    public static class ChatServerThread extends Thread {
        private Socket socket;
        private ChatServer chatServer;
        private OutputStreamWriter writer;
        private BufferedReader reader;

        public ChatServerThread(Socket socket, ChatServer chatServer) {
            this.socket = socket;
            this.chatServer = chatServer;
            try {
                this.writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            } catch (Exception e) {
                System.out.println("ChatServerThread 생성자 에러 : " + e);
            }
        }

        public void run() {
            try {
                while (true) {
                    String message = reader.readLine();
                    if (message == null) {
                        break;
                    }

                    chatServer.broadcast(message, this);
                }
            } catch (Exception e) {
                System.out.println("ChatServerThread run 에러 : " + e);
            } finally {
                chatServer.removeThread(this);
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (Exception e) {
                    System.out.println("ChatServerThread 소켓 종료 에러 : " + e);
                }
            }
        }

        public void sendMessage(String message) {
            try {
                writer.write(message + "\r\n");
                writer.flush();
            } catch (Exception e) {
                System.out.println("ChatServerThread sendMessage 에러 : " + e);
            }
        }
    }
}
