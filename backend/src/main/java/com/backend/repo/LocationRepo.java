package com.backend.repo;

import com.backend.model.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<LocationModel, Long> {
}
