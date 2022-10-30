package com.backend.repo;

import com.backend.model.DistrictModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DistrictRepo extends JpaRepository<DistrictModel, Integer> {

    @Query("select district from DistrictModel district where postal_sector=?1")
    Optional<DistrictModel> getDistrictByPostalSector(String postalSector);
}
