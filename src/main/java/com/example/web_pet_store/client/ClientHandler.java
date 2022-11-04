package com.example.web_pet_store.client;

import com.alibaba.fastjson2.JSON;
import com.example.web_pet_store.model.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler {
    public static int userId = -1;
    private Scanner scanner = new Scanner(System.in);
    ClientTCPReceiver clientTCPReceiver;
    Socket socket;
    Thread thread;

    public void mainLoop() throws IOException {
        clientHandle();
    }

    private void clientHandle() throws IOException {
        System.out.println("请输入服务器IP地址：");
        String ip = scanner.next();
        System.out.println("请输入监听的端口号:");
        int port = scanner.nextInt();
        System.out.println("请输入您的昵称：");
        String nickName = scanner.next();

        socket = new Socket(ip,port);
        clientTCPReceiver = new ClientTCPReceiver(socket);
        thread = new Thread(clientTCPReceiver);
        thread.start();
        Message message = new Message();
        message.setCommand("LOGIN");
        message.setNickName(nickName);
        byte[] messageByte = JSON.toJSONString(message).getBytes();
        socket.getOutputStream().write(messageByte);
        while(userId == -1){
            System.out.println("等待服务器返回注册信息，请稍等。。。");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        while(true){
            System.out.println("输入1 查看宠物列表");
            System.out.println("输入2 查看活动列表");
            System.out.println("输入3 购买宠物");
            System.out.println("输入q 退出");
            String str = scanner.next();
            switch(str){
                case "1":
                    Message message1 = new Message();
                    message1.setCommand("PET_LIST");
                    byte[] messageByte1 = JSON.toJSONString(message1).getBytes();
                    socket.getOutputStream().write(messageByte1);
                    break;
                case "2":
                    Message message2 = new Message();
                    message2.setCommand("ACT_LIST");
                    byte[] messageByte2 = JSON.toJSONString(message2).getBytes();
                    socket.getOutputStream().write(messageByte2);
                    break;
                case "3":
                    System.out.println("请输入宠物ID:");
                    int id = scanner.nextInt();
                    Message message3 = new Message();
                    message3.setUserId(userId);
                    message3.setNickName(nickName);
                    message3.setPetId(id);
                    message3.setCommand("BUY_PET");
                    byte[] messageByte3 = JSON.toJSONString(message3).getBytes();
                    socket.getOutputStream().write(messageByte3);
                    break;
                case "s":
                    admin();
                    break;
                case "q":
                    Message message4 = new Message();
                    message4.setCommand("LOGOUT");
                    socket.getOutputStream().write(JSON.toJSONString(message4).getBytes());
                    break;

            }
        }
    }


    private void admin() throws IOException {
        System.out.println("输入1 上架宠物");
        System.out.println("输入2 添加活动");
        System.out.println("输入3 调整宠物状态");
        System.out.println("输入4 查看商店流水");
        System.out.println("输入5 查看当前宠物的平均价格");
        System.out.println("输入6 查看各类宠物的最高价格");
        System.out.println("输入7 查看各类宠物的最低价格");
        System.out.println("输入q 退出");
        String num = scanner.next();
        switch(num){
            case "1":
                System.out.println("请输入宠物id：");
                int id = scanner.nextInt();
                System.out.println("请输入宠物姓名：");
                String name = scanner.next();
                System.out.println("请输入宠物类型：");
                int type = scanner.nextInt();
                System.out.println("请输入宠物价格：");
                double price = scanner.nextDouble();
                System.out.println("请输入宠物的状态：");
                int status = scanner.nextInt();
                Message message = new Message();
                message.setPetId(id);
                message.setPetName(name);
                message.setPetType(type);
                message.setPetPrice(price);
                message.setPetStatus(status);
                message.setCommand("NEW_PET");
                byte[] messageByte = JSON.toJSONString(message).getBytes();
                socket.getOutputStream().write(messageByte);
                break;
            case "2":
                System.out.println("请输入活动id：");
                int actId = scanner.nextInt();
                System.out.println("请输入活动姓名：");
                String actName = scanner.next();
                System.out.println("请输入活动宠物类型：");
                int actPetType = scanner.nextInt();
                System.out.println("请输入活动折扣：");
                double actPetRebate = scanner.nextDouble();
                System.out.println("请输入活动状态：");
                int actStatus = scanner.nextInt();
                Message message1 = new Message();
                message1.setActId(actId);
                message1.setActName(actName);
                message1.setActPetType(actPetType);
                message1.setActRebate(actPetRebate);
                message1.setActStatus(actStatus);
                message1.setCommand("NEW_ACT");
                byte[] messageByte1 = JSON.toJSONString(message1).getBytes();
                socket.getOutputStream().write(messageByte1);
                break;
            case "3":
                System.out.println("请输入宠物的id");
                int petId = scanner.nextInt();
                System.out.println("请输入宠物的状态");
                int petStatus = scanner.nextInt();
                Message message2 = new Message();
                message2.setActId(petId);
                message2.setActStatus(petStatus);
                message2.setCommand("PET_STATUS");
                byte[] messageByte2 = JSON.toJSONString(message2).getBytes();
                socket.getOutputStream().write(messageByte2);
                break;
            case "4":
                Message message3 = new Message();
                message3.setCommand("USER_LIST");
                socket.getOutputStream().write(JSON.toJSONString(message3).getBytes());
                break;
            case "5":
                Message message4 = new Message();
                message4.setCommand("PET_AVE");
                socket.getOutputStream().write(JSON.toJSONString(message4).getBytes());
                break;
            case "6":
                Message message5 = new Message();
                message5.setCommand("PET_MAX");
                socket.getOutputStream().write(JSON.toJSONString(message5).getBytes());
                break;
            case "7":
                Message message6 = new Message();
                message6.setCommand("PET_MIN");
                socket.getOutputStream().write(JSON.toJSONString(message6).getBytes());
                break;
            case "q":
                Message message7 = new Message();
                message7.setCommand("QUIT");
                socket.getOutputStream().write(JSON.toJSONString(message7).getBytes());
                break;
        }
    }
}
