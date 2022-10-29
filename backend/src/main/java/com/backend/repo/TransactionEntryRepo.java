package com.backend.repo;

import com.backend.model.TransactionEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionEntryRepo extends JpaRepository<TransactionEntryModel, Long> {
}
