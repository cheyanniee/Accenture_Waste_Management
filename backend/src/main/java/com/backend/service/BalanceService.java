package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.BalanceModel;
import com.backend.model.PeopleModel;
import com.backend.model.TransactionModel;
import com.backend.repo.BalanceRepo;
import com.backend.repo.PeopleRepo;
import com.backend.request.PeopleRequest;
import com.backend.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // create balance alongside people
    public void createBalance (PeopleModel peopleModel) {
        BalanceModel balanceModel = BalanceModel.builder()
                .currentBalance(0F)
                .peopleModel(peopleModel)
                .build();

        balanceRepo.save(balanceModel);
    }

    //manual delete balance
    public void deleteBalance (PeopleModel peopleModel) throws Exception {

        BalanceModel balanceModel = balanceRepo.getBalanceByPeopleId(peopleModel.getId()).orElseThrow(() -> new Exception("Unable to find balance of user"));
        balanceRepo.delete(balanceModel);
    }

    public void updateBalanceByTransaction (TransactionRequest transactionRequest) throws CustomException{

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
}
