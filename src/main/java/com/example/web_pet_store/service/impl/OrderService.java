package com.example.web_pet_store.service.impl;

import com.example.web_pet_store.model.Order;
import com.example.web_pet_store.model.Pet;
import com.example.web_pet_store.service.IOrderService;
import com.example.web_pet_store.service.IPetService;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {

    private static OrderService orderService = new OrderService();
    private OrderService(){
    }
    public static OrderService getInstance(){
        return orderService;
    }
    private List<Order> orderList = new ArrayList<>();
    private List<Pet> petList = PetService.getInstance().getPetList();
    @Override
    public void newOrder(int id, String name, int petId, double amount) {
        Order order = new Order();
        order.setId(id);
        order.setName(name);
        order.setPetId(petId);
        order.setAmount(orderService.amount());
        orderList.add(order);
    }

    @Override
    public double amount(int petId) {

    }

    @Override
    public List<Order> getOrderList() {
        return null;
    }
}
