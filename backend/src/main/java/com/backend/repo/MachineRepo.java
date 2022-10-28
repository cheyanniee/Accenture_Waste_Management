package com.backend.repo;

import com.backend.model.MachineModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepo extends JpaRepository<MachineModel, Integer> {
}
