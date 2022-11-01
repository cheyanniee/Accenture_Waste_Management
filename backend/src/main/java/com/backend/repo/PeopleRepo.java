package com.backend.repo;

import com.backend.model.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/*
    Purpose:
        - Repository to define JPA queries for people

    Author:
        - Liu Fang, Lew Xu Hong
*/

public interface PeopleRepo extends JpaRepository<PeopleModel, Long> {

    @Query("select people from PeopleModel people where email=?1")
    Optional<PeopleModel> getPeopleByEmail(String email);

    @Query("SELECT people from PeopleModel people where email=?1 and password=?2")
    Optional<PeopleModel> getPeopleByEmailAndPassword(String email, String password);

    @Query("select people from PeopleModel people where official_id=?1")
    Optional<PeopleModel> getPeopleByOfficialId(String officialId);

    @Modifying
    @Transactional
    @Query("update PeopleModel set token = ?1 where id = ?2")
    Integer updateTokenByPeopleId(String token, Long peopleId);

    @Modifying
    @Transactional
    @Query("update PeopleModel set last_login = ?1 where id = ?2")
    Integer updateLastLoginByPeopleId(ZonedDateTime dtLogin, Long peopleId);


}
