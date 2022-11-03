package com.example.web_pet_store.service;

import com.example.web_pet_store.model.Order;

import java.util.List;

public interface IOrderService {
    void newOrder(int id,String name,int petId,double amount);
    double amount(int petId);
    List<Order> getOrderList();
}
