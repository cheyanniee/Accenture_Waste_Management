package com.backend.service;


import com.backend.model.TransactionModel;
import com.backend.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionEntryService {

    @Autowired
    TransactionRepo transactionRepo;

    public List<TransactionModel> listTransaction() {
        return transactionRepo.findAll();
    }
}
