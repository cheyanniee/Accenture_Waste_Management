package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.BalanceModel;
import com.backend.model.PeopleModel;
import com.backend.repo.BalanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<BalanceModel> getBalanceByPeopleId(PeopleModel peopleModel) throws CustomException {

        return balanceRepo.getBalanceByPeopleId(peopleModel.getId());
    }

    public void createBalance (PeopleModel peopleModel) {
        BalanceModel balanceModel = BalanceModel.builder()
                .currentBalance(0)
                .peopleModel(peopleModel)
                .build();

        balanceRepo.save(balanceModel);
    }

    public void deleteBalance (PeopleModel peopleModel) throws Exception {

        BalanceModel balanceModel = balanceRepo.getBalanceByPeopleId(peopleModel.getId()).orElseThrow(() -> new Exception("Unable to find balance of user"));
        balanceRepo.delete(balanceModel);
    }

}
