package com.example.web_pet_store;

import com.example.web_pet_store.client.ClientHandler;
import com.example.web_pet_store.server.Session;
import com.example.web_pet_store.server.SessionManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@SpringBootApplication
public class WebPetStoreApplication implements CommandLineRunner {
    static int id = 0;
    public static int getId(){
        id = id + 1;
        return id;
    }

    public static SessionManager sessionManager = SessionManager.getInstance();
    public static void main(String[] args) {
        SpringApplication.run(WebPetStoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入模式：1服务端 2客户端");
        String str = scanner.next();
        if("1".equals(str)){
            serverHandle(scanner);
        }else{
            ClientHandler clientHandler = new ClientHandler();
            clientHandler.mainLoop();
        }
    }

    private void serverHandle(Scanner scanner) throws IOException {
        System.out.println("请输入监听的端口号：");
        int port = scanner.nextInt();

        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            Socket connect = serverSocket.accept();
            System.out.println("用户连接");
            Session session = new Session(connect);
            sessionManager.addSession(session);
        }
    }
}
