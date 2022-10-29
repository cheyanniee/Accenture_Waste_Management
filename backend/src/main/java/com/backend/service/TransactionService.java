package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.*;
import com.backend.repo.TransactionEntryRepo;
import com.backend.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepo transactionRepo;

    public List<TransactionModel> listTransaction() {
        return transactionRepo.findAll();
    }

    public Optional<List<TransactionModel>> getTransactionByPeopleId(PeopleModel peopleModel) throws CustomException {
        return transactionRepo.getTransactionByPeopleId(peopleModel.getId());
    }

//    //incomplete createTransaction - DO
//    public void createTransaction (PeopleModel peopleModel, MachineModel machineModel) {
//        ZoneId zid = ZoneId.of("Asia/Singapore");
//
//        TransactionModel transactionModel = TransactionModel.builder()
//                .peopleModel(peopleModel)
//                .machineModel(machineModel)
//              //  .transactionEntry(new ArrayList<TransactionEntryModel>()) //to be determined - how to create for this part?
//                .balanceChange(19) //hardcoded for now
//                .dateAndTime(ZonedDateTime.now(zid)) //check this point against the other timestamps
//                .build();
//        transactionRepo.save(transactionModel);
//    }

}
