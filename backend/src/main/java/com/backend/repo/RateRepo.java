package com.backend.repo;

import com.backend.model.BatteryModel;
import com.backend.model.RateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RateRepo extends JpaRepository<RateModel, Integer> {

    @Query("select rate from RateModel rate where type=?1")
    Optional<RateModel> getRateByType(String type);

}
