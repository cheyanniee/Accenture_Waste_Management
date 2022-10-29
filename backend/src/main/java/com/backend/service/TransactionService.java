package com.backend.service;

import com.backend.model.TransactionEntryModel;
import com.backend.repo.TransactionEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionEntryRepo transactionEntryRepo;

    public List<TransactionEntryModel> listTransaction() {
        return transactionEntryRepo.findAll();
    }
}
