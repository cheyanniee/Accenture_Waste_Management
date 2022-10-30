package com.backend.repo;

import com.backend.model.TransactionEntryModel;
import com.backend.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionEntryRepo extends JpaRepository<TransactionEntryModel, Long> {

    //find by peopletoken then view

    @Query("select transactionEntry from TransactionEntryModel transactionEntry where transaction_id=?1")
    public Optional<List<TransactionEntryModel>> getTransactionEntryByTransactionId (Long people_id);


}
