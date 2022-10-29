package com.backend.service;

import com.backend.model.ForgotPasswordModel;
import com.backend.repo.ForgotPasswordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForgotPasswordService {

    @Autowired
    ForgotPasswordRepo forgotPasswordRepo;

    public List<ForgotPasswordModel> listAllForgotPassword() {
        return forgotPasswordRepo.findAll();
    }
}
