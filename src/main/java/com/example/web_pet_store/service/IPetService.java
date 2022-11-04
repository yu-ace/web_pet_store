package com.example.web_pet_store.service;

import com.example.web_pet_store.model.Pet;

import java.util.List;

public interface IPetService {
    void newPet(int id,String name,int type,double price,int status);
    List<Pet> getPetList();
    double[] ave();
    Pet[] max();
    Pet[] min();
    void changePetStatus(int petId,int status);
}
