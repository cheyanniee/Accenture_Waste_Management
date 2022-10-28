package com.backend.repo;

import com.backend.model.ForgotPasswordModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordModel, Integer>{

}
