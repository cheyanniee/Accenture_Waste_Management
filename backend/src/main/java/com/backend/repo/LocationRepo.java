package com.backend.repo;

import com.backend.model.LocationModel;
import com.backend.model.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<LocationModel, Long> {

}
