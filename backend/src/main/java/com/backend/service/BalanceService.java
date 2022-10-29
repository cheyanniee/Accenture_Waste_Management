package com.backend.service;

import com.backend.model.BalanceModel;
import com.backend.repo.BalanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService {

    @Autowired
    BalanceRepo balanceRepo;

    public List<BalanceModel> listBalance() {
        return balanceRepo.findAll();
    }

}
