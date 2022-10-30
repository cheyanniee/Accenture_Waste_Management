package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.DistrictModel;
import com.backend.repo.DistrictRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    @Autowired
    DistrictRepo districtRepo;

    public List<DistrictModel> listAllDistrict() {
        return districtRepo.findAll();
    }

    public DistrictModel findDistrictByPostalSector(String postalSector) throws CustomException {
        return districtRepo.getDistrictByPostalSector(postalSector).orElseThrow(()-> new CustomException("District's postal not exists."));
    }
}
