package com.example.web_pet_store.service;

import com.example.web_pet_store.model.TransactionRecord;

import java.util.List;

public interface ITransactionRecordService {
    void newTransactionRecord(int id,int orderId,int petId,double amount);
    List<TransactionRecord> getTransactionRecord();
}
