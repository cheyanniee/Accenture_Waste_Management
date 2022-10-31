package com.backend.repo;

import com.backend.model.ForgotPasswordModel;
import com.backend.model.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordModel, Long>{

    @Query("select forgotpasswod from ForgotPasswordModel forgotpasswod where peopleModel=?1")
    Optional<ForgotPasswordModel> getForgotPasswordByPeople(PeopleModel peopleModel);

    List<ForgotPasswordModel> findAllByPeopleModel(PeopleModel peopleModel);

    List<ForgotPasswordModel> findAllByPeopleModelEmail(String email);

    List<ForgotPasswordModel> findAllByPeopleModelEmailOrderById(String email);

}
