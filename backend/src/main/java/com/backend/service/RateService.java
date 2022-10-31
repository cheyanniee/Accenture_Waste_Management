package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.RateModel;
import com.backend.repo.RateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateService {

    @Autowired
    RateRepo rateRepo;

    public List<RateModel> listAllRate() {
        return rateRepo.findAll();
    }

//    public void createRate(RateRequest rateRequest) throws CustomException {
//        Optional<RateModel> rateExist = rateRepo.getRateByType(rateRequest.getType());
//        if (rateExist.isPresent()) {
//            throw new CustomException("Rate type exists.");
//        }
//
//        RateModel rateModel = RateModel.builder()
//                .type(rateRequest.getType())
//                .pointsPerUnit(rateRequest.getPointsPerUnit())
//                .build();
//        rateRepo.save(rateModel);
//    }
//
//    public boolean updateRate(RateRequest rateRequest) throws CustomException {
//        RateModel rate = rateRepo.findById(rateRequest.getId()).orElseThrow(() -> new CustomException("Rate is not found!"));
//        if (rateRequest.getType() != null && !rateRequest.getType().equals("")) {
//            rate.setType(rateRequest.getType());
//        }
//
//        if (rateRequest.getPointsPerUnit() != null && rateRequest.getPointsPerUnit() != 0) {
//            rate.setPointsPerUnit(rateRequest.getPointsPerUnit());
//        }
//        rateRepo.save(rate);
//        return true;
//    }
//
//    public List<RateModel> deleteRate(RateRequest rateRequest){
//        return rateRepo.deleteByType(rateRequest.getType());
//    }

}
