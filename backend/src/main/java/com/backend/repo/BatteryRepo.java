package com.backend.repo;

import com.backend.model.BatteryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatteryRepo extends JpaRepository<BatteryModel, Integer>{
}
