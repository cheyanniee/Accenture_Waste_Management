package com.backend.service;

import com.backend.model.RateModel;
import com.backend.repo.RateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateService {

    @Autowired
    RateRepo rateRepo;

    public List<RateModel> listAllRate() {
        return rateRepo.findAll();
    }


}
