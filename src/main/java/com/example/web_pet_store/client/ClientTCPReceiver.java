package com.example.web_pet_store.client;

import com.alibaba.fastjson2.JSON;
import com.example.web_pet_store.model.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientTCPReceiver implements Runnable{
    private Socket socket;
    public ClientTCPReceiver(Socket socket){
        this.socket =socket;
    }

    @Override
    public void run() {
        while(true){
            byte[] buffer = new byte[1024];
            try {
                InputStream in = socket.getInputStream();
                in.read(buffer);
                String message = new String(buffer);
                Message message1 = JSON.parseObject(message.trim(), Message.class);
                switch(message1.getCommand()){
                    case "MESSAGE":
                        System.out.println(message1.getConnect());
                        break;
                    case "LOGIN_OK":
                        int userId = message1.getUserId();
                        ClientHandler.userId = userId;
                        break;
                    case "QUIT":
                        System.exit(0);
                        break;
                    case "BYE":
                        System.out.println("服务器已关闭连接，再见");
                        System.exit(0);
                        break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
