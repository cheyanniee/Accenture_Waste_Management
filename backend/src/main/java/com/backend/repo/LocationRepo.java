package com.backend.repo;

import com.backend.model.LocationModel;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationRepo extends JpaRepository<LocationModel, Long> {

    @Query("select location from LocationModel location where postcode=?1")
    Optional<LocationModel> getLocationByPostcode(String postcode);

}
