package com.example.web_pet_store.model;

public class Message {
    private int userId;
    private String nickName;
    private String command;
    private int petId;
    private String petName;
    private int petType;
    private double petPrice;
    private int petStatus;
    private int actId;
    private String actName;
    private int actPetType;
    private double actRebate;
    private int actStatus;
    private String connect;

    public Message(){
    }

    public Message(String command, String connect) {
        this.command = command;
        this.connect = connect;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public int getActId() {
        return actId;
    }

    public void setActId(int actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public int getActPetType() {
        return actPetType;
    }

    public void setActPetType(int actPetType) {
        this.actPetType = actPetType;
    }

    public double getActRebate() {
        return actRebate;
    }

    public void setActRebate(double actRebate) {
        this.actRebate = actRebate;
    }

    public int getActStatus() {
        return actStatus;
    }

    public void setActStatus(int actStatus) {
        this.actStatus = actStatus;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getPetType() {
        return petType;
    }

    public void setPetType(int petType) {
        this.petType = petType;
    }

    public double getPetPrice() {
        return petPrice;
    }

    public void setPetPrice(double petPrice) {
        this.petPrice = petPrice;
    }

    public int getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(int petStatus) {
        this.petStatus = petStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }


    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }
}
