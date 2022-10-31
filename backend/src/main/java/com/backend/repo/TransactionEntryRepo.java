package com.backend.repo;

import com.backend.model.TransactionEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
/*
    Purpose:
        - Repo to execute SQL queries for TransactionEntry

    Author:
        - Lew Xu Hong
*/

public interface TransactionEntryRepo extends JpaRepository<TransactionEntryModel, Long> {

    @Query("select transactionEntry from TransactionEntryModel transactionEntry where transaction_id=?1")
    public Optional<List<TransactionEntryModel>> getTransactionEntryByTransactionId (Long people_id);


}
