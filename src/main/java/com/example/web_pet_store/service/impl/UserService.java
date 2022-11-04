package com.example.web_pet_store.service.impl;

import com.example.web_pet_store.model.Act;
import com.example.web_pet_store.model.User;
import com.example.web_pet_store.model.Pet;
import com.example.web_pet_store.service.IUserService;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {

    private static UserService orderService = new UserService();
    private UserService(){
    }
    public static UserService getInstance(){
        return orderService;
    }
    private List<User> orderList = new ArrayList<>();
    private List<Pet> petList = PetService.getInstance().getPetList();
    private List<Act> actList = ActService.getInstance().getActList();
    @Override
    public void newUser(int id, String name, int petId) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPetId(petId);
        user.setAmount(orderService.amount(petId));
        orderList.add(user);
    }

    @Override
    public double amount(int petId) {
        double amount = 0;
        for(Pet pet:petList){
            if(pet.getId() == petId && pet.getStatus() == 0){
                for(Act act:actList){
                    if(act.getPetType() == pet.getType() && act.getStatus() == 0){
                        amount = pet.getPrice() * act.getRebate();
                    }else{
                        amount = pet.getPrice();
                    }
                }
            }
        }
        return amount;
    }

    @Override
    public List<User> getUserList() {
        return orderList;
    }
}
