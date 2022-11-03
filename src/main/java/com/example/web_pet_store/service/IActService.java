package com.example.web_pet_store.service;

import com.example.web_pet_store.model.Act;

import java.util.List;

public interface IActService {
    void newAct(int id,String name,int petType,double rebate,int status);
    List<Act> getActList();
}
