package com.backend.repo;

import com.backend.model.BatteryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BatteryRepo extends JpaRepository<BatteryModel, Integer>{

    @Query("select battery from BatteryModel battery where type=?1")
    Optional<BatteryModel> getBatteryByType(String type);
}
