package com.example.web_pet_store.server;

import com.alibaba.fastjson2.JSON;
import com.example.web_pet_store.WebPetStoreApplication;
import com.example.web_pet_store.model.Act;
import com.example.web_pet_store.model.Message;
import com.example.web_pet_store.model.Pet;
import com.example.web_pet_store.model.User;
import com.example.web_pet_store.service.IActService;
import com.example.web_pet_store.service.IPetService;
import com.example.web_pet_store.service.IUserService;
import com.example.web_pet_store.service.impl.ActService;
import com.example.web_pet_store.service.impl.PetService;
import com.example.web_pet_store.service.impl.UserService;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.List;

public class Session implements Runnable , Closeable {
    User user;
    Socket socket;
    Thread thread;
    boolean isRunning = true;
    private IPetService petService = PetService.getInstance();
    private IActService actService = ActService.getInstance();
    private IUserService userService = UserService.getInstance();


    public Session(){
    }

    public Session(Socket socket){
        this.socket =socket;
        thread = new Thread(this);
        thread.start();
        Message message = new Message();
        message.setCommand("MESSAGE");
        message.setConnect("欢迎来到宠物商店");
        try {
            socket.getOutputStream().write(JSON.toJSONString(message).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void close() throws IOException {
        System.out.println("用户退出，关闭线程，释放资源");
        //todo;关闭渠道
        thread.interrupt();
        socket.close();
    }

    @Override
    public void run() {
        while(true){
            try {
                byte[] buffer = new byte[1024];
                InputStream in = socket.getInputStream();
                in.read(buffer);
                String message = new String(buffer);
                message = message.trim();
                System.out.printf("接收到消息：%s",message);
                Message message1 = JSON.parseObject(message.trim(), Message.class);
                handleCommand(message1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleCommand(Message message){
        switch(message.getCommand()){
            case "LOGIN":
                User user1 = new User();
                user1.setId(WebPetStoreApplication.getId());
                user1.setName(message.getNickName());
                this.setUser(user1);

                Message message1 = new Message();
                message1.setUserId(user1.getId());
                message1.setCommand("LOGIN_OK");
                try {
                    socket.getOutputStream().write(JSON.toJSONString(message1).getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "PET_LIST":
                List<Pet> petList = petService.getPetList();
                String ls = " ";
                for(Pet pet:petList){
                    String line = String.format("%d %s %d %.2f %d",pet.getId(),
                            pet.getName(),pet.getType(),pet.getPrice(),pet.getStatus());
                    ls = ls + line + "\n";
                }
                sendSystemMessage(ls);
                break;
            case "ACT_LIST":
                List<Act> actList = actService.getActList();
                String ls1 = "";
                for(Act act:actList){
                    String line1 = String.format("%d %s %d %.2f %d",act.getId(),
                            act.getName(),act.getPetType(),act.getRebate(),act.getStatus());
                    ls1 = ls1 + line1;
                }
                sendSystemMessage(ls1);
                break;
            case "BUY_PET":
                userService.newUser(message.getUserId(),message.getNickName(),message.getPetId());
                sendSystemMessage("请支付：" + userService.amount(message.getPetId()) + "元");
                petService.changePetStatus(message.getPetId(),1);
                break;
            case "NEW_PET":
                petService.newPet(message.getPetId(),message.getPetName(),message.getPetType(),
                        message.getPetPrice(),message.getPetStatus());
                sendSystemMessage("创建成功");
                break;
            case "NEW_ACT":
                actService.newAct(message.getActId(),message.getActName(),message.getActPetType(),
                        message.getActRebate(),message.getActStatus());
                sendSystemMessage("创建成功");
                break;
            case "PET_STATUS":
                petService.changePetStatus(message.getPetId(),message.getPetStatus());
                sendSystemMessage("宠物id为:" + message.getPetId() + "的状态已经调整为：" + message.getPetStatus());
                break;
            case "USER_LIST":
                List<User> userList = userService.getUserList();
                String ls2 = "";
                for(User user2:userList){
                    String line2 = String.format("%d %s %d %.2f",user2.getId(),
                            user2.getName(),user2.getPetId(),user2.getAmount());
                    ls2 = ls2 + line2;
                }
                sendSystemMessage(ls2);
                break;
            case "PET_AVE":
                double[] ave = petService.ave();
                String ls3 = "";
                for(int i = 0;i < 2;i++){
                    String a = "猫";
                    if(i == 1){
                        a = "狗";
                    }
                    String line3 = String.format("宠物%s的平均价格为%.2f",a,ave[i]);
                    ls3 = ls3 + line3;
                }
                sendSystemMessage(ls3);
                break;
            case "PET_MAX":
                Pet[] pets = petService.max();
                String ls4 = "";
                for(int i = 0;i < 2;i++){
                    String a = "猫";
                    if(i == 1){
                        a = "狗";
                    }
                    String line4 = String.format("宠物%s的最高价为：.2f",a,pets[i]);
                    ls4 = ls4 + line4;
                }
                sendSystemMessage(ls4);
                break;
            case "PET_MIN":
                Pet[] pets1 = petService.min();
                String ls5 = "";
                for(int i = 0;i < 2;i++){
                    String a1 = "猫";
                    if(i == 1){
                        a1 = "狗";
                    }
                    String line5 = String.format("宠物%s的最低价格为：.2f",a1,pets1[i]);
                    ls5 = ls5 + line5;
                }
                sendSystemMessage(ls5);
                break;
            case "QUIT":
                Message message2 = new Message();
                message2.setCommand("QUIT");
                try {
                    socket.getOutputStream().write(JSON.toJSONString(message2).getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "LOGOUT":
                System.out.printf("用户%s退出",message.getNickName());
                Message message3 = new Message();
                message3.setCommand("BYE");
                try {
                    socket.getOutputStream().write(JSON.toJSONString(message3).getBytes());
                    this.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    public void sendSystemMessage(String message){
        Message message1 = new Message("MESSAGE",message);
        try {
            socket.getOutputStream().write(JSON.toJSONString(message1).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
