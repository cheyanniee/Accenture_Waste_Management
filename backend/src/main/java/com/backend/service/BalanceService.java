package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.BalanceModel;
import com.backend.model.PeopleModel;
import com.backend.model.TransactionModel;
import com.backend.repo.BalanceRepo;
import com.backend.request.BalanceRequest;
import com.backend.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    Purpose:
        - Service methods to be used in Balance-related APIs or assist in other functions
        - Major functions: checking if balance is enough for exchange, updating balance when transaction is confirmed

    Author:
        - Lew Xu Hong
*/

@Service
public class BalanceService {

    @Autowired
    BalanceRepo balanceRepo;

    public List<BalanceModel> listBalance() {
        return balanceRepo.findAll();
    }

    public BalanceModel getBalanceByPeopleId(PeopleModel peopleModel) throws Exception {
        return balanceRepo.getBalanceByPeopleId(peopleModel.getId()).orElseThrow(()-> new Exception("Unable to find balance"));
    }

    //To create BalanceModel when PeopleModel created.
    public void createBalance (PeopleModel peopleModel) {
        BalanceModel balanceModel = BalanceModel.builder()
                .currentBalance(0F)
                .peopleModel(peopleModel)
                .build();
        balanceRepo.save(balanceModel);
    }

    //Delete BalanceModel by PeopleModel's id
    public void deleteBalance (PeopleModel peopleModel) throws Exception {
        BalanceModel balanceModel = balanceRepo.getBalanceByPeopleId(peopleModel.getId()).orElseThrow(() -> new Exception("Unable to find balance of user"));
        balanceRepo.delete(balanceModel);
    }

    //Ensure there is enough points in the PeopleModel's BalanceModel to deduct
    public boolean checkBalanceForExchange(TransactionRequest transactionRequest) throws Exception {
        Float balanceChange = transactionRequest.getBalanceChange();
        BalanceModel balanceModel = balanceRepo.getBalanceByPeopleId(transactionRequest.getPeopleId()).orElseThrow(()-> new CustomException("No balance found with user"));
        Float currentBalance = balanceModel.getCurrentBalance();
        if (balanceChange > currentBalance){
            return false;
        }
        return true;
    }

    //Update changes in BalanceModel's points after transaction at machine is completed
    public void updateBalanceByTransaction (TransactionRequest transactionRequest) throws Exception{
        Float balanceChange = transactionRequest.getBalanceChange();
        BalanceModel balanceModel = balanceRepo.getBalanceByPeopleId(transactionRequest.getPeopleId()).orElseThrow(()-> new CustomException("No balance found with user"));
        Float currentBalance = balanceModel.getCurrentBalance();

        Float newBalance = 0F;

        if (transactionRequest.getChooseType() == TransactionModel.Choose.exchange){
            newBalance = currentBalance - balanceChange;
        }
        if (transactionRequest.getChooseType() == TransactionModel.Choose.recycle){
            newBalance = currentBalance + balanceChange;
        }

        balanceModel.setCurrentBalance(newBalance);
        balanceRepo.updateBalanceById(newBalance, balanceModel.getId());
        balanceRepo.save(balanceModel);
    }

    //Allows manual update of specific BalanceModel
    public void updateBalance(BalanceRequest balanceRequest, Long id) throws Exception{
        BalanceModel balanceModel = balanceRepo.findById(id).orElseThrow(() -> new CustomException("Balance not found!"));

        if (balanceRequest.getCurrentBalance() !=null) {
            balanceModel.setCurrentBalance(balanceRequest.getCurrentBalance());
        }
    }
}
