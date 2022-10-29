package com.backend.repo;

import com.backend.model.BalanceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepo extends JpaRepository<BalanceModel, Long> {
}
