package com.backend.repo;

import com.backend.model.LocationModel;
<<<<<<< HEAD
<<<<<<< HEAD
import com.backend.model.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<LocationModel, Long> {

=======
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<LocationModel, Long> {
>>>>>>> a3fa898297d851869882f090d416ed9d904184c7
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocationRepo extends JpaRepository<LocationModel, Long> {

    @Query("select location from LocationModel location where postcode=?1")
    Optional<LocationModel> getLocationByPostcode(String postcode);
>>>>>>> 125969bc266b68151b71b84b54d8cb9acd1dbfbc
}
