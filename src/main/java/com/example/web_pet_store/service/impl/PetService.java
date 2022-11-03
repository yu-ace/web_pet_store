package com.example.web_pet_store.service.impl;

import com.example.web_pet_store.model.Pet;
import com.example.web_pet_store.service.IPetService;

import java.util.ArrayList;
import java.util.List;

public class PetService implements IPetService {

    private static PetService petService = new PetService();
    private PetService(){
    }
    public static PetService getInstance(){
        return petService;
    }
    private List<Pet> petList = new ArrayList<>();

    @Override
    public void newPet(int id, String name, int type, double price, int status) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName(name);
        pet.setType(type);
        pet.setPrice(price);
        pet.setStatus(status);
        petList.add(pet);
    }

    @Override
    public List<Pet> getPetList() {
        return petList;
    }

    @Override
    public double[] ave() {
        double[] sum = new double[2];
        int[] num = new int[2];
        double[] ave = new double[2];
        for(Pet pet:petList){
            if(pet.getType() == 0){
                sum[0] = sum[0] + pet.getPrice();
                num[0]++;
            }else{
                sum[1] = sum[1] + pet.getPrice();
                num[1]++;
            }
        }
        for(int i = 0;i < 2;i++){
            ave[i] = sum[i] / num[i];
        }
        return ave;
    }

    @Override
    public Pet[] max() {
        Pet[] pets = new Pet[2];
        for(Pet pet:petList){
            if(pet.getType() == 0){
                if(pets[0] == null){
                    pets[0] = pet;
                }else if(pets[0].getPrice() < pet.getPrice()){
                    pets[0] = pet;
                }
            }else{
                if(pets[1] == null){
                    pets[1] = pet;
                }else if(pets[1].getPrice() < pet.getPrice()){
                    pets[1] = pet;
                }
            }
        }
        return pets;
    }

    @Override
    public Pet[] min() {
        Pet[] pets = new Pet[2];
        for(Pet pet:petList){
            if(pet.getPrice() == 0){
                if(pets[0] == null){
                    pets[0] = pet;
                }else if(pets[0].getPrice() > pet.getPrice()){
                    pets[0] = pet;
                }
            }else{
                if(pets[1] == null){
                    pets[1] = pet;
                }else if(pets[1].getPrice() > pet.getPrice()){
                    pets[1] = pet;
                }
            }
        }
        return pets;
    }

    @Override
    public void petStatus(int petId, int status) {
        for(Pet pet : petList){
            if(pet.getId() == petId){
                pet.setStatus(status);
            }
        }
    }
}
