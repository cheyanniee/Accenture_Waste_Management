package com.backend.service;


import com.backend.configuration.CustomException;
import com.backend.model.BalanceModel;
import com.backend.model.PeopleModel;
import com.backend.model.TransactionEntryModel;
import com.backend.model.TransactionModel;
import com.backend.repo.TransactionEntryRepo;
import com.backend.repo.TransactionRepo;
import com.backend.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionEntryService {

    @Autowired
    TransactionEntryRepo transactionEntryRepo;

    @Autowired
    TransactionRepo transactionRepo;

    public List<TransactionEntryModel> listAllTransactionEntry() {return transactionEntryRepo.findAll();}

//    public Optional<List<TransactionEntryModel>> getTransactionEntryByTransactionId(TransactionModel transactionModel) throws CustomException {
//        return transactionEntryRepo.getTransactionEntryByTransactionId(transactionModel.getId());
//    }
    public Optional<List<TransactionEntryModel>> getTransactionEntryByTransactionId(Long transaction_id) throws CustomException {
        return transactionEntryRepo.getTransactionEntryByTransactionId(transaction_id);
    }

}
