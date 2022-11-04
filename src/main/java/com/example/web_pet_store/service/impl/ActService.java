package com.example.web_pet_store.service.impl;

import com.example.web_pet_store.model.Act;
import com.example.web_pet_store.service.IActService;

import java.util.ArrayList;
import java.util.List;

public class ActService implements IActService {

    private static ActService actService = new ActService();
    private ActService(){
    }
    public static ActService getInstance(){
        return actService;
    }
    private List<Act> actList = new ArrayList<>();
    @Override
    public void newAct(int id, String name, int petType, double rebate, int status) {
        Act act = new Act();
        act.setId(id);
        act.setName(name);
        act.setPetType(petType);
        act.setRebate(rebate);
        act.setStatus(status);
        actList.add(act);
    }

    @Override
    public List<Act> getActList() {
        return actList;
    }
}
