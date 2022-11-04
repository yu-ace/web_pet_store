package com.example.web_pet_store.service;

import com.example.web_pet_store.model.User;

import java.util.List;

public interface IUserService {
    void newUser(int id,String name,int petId);
    double amount(int petId);
    List<User> getUserList();
}
