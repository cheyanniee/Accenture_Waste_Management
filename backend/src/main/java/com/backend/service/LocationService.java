package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.DistrictModel;
import com.backend.model.LocationModel;
import com.backend.repo.LocationRepo;
import com.backend.request.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocationService {

    @Autowired
    LocationRepo locationRepo;

    @Autowired
    DistrictService districtService;

    public List<LocationModel> listAllLocation() {
        return locationRepo.findAll();
    }

    public void createLocation(LocationRequest locationRequest) throws CustomException {
        String postalSector = locationRequest.getPostcode().substring(0,2);
        DistrictModel district = districtService.findDistrictByPostalSector(postalSector);

        LocationModel locationNew = LocationModel.builder()
                .address(locationRequest.getAddress())
                .postcode(locationRequest.getPostcode())
                .districtModel(district)
                .build();

        locationRepo.save(locationNew);
    }

    public LocationModel findLocationByPostcode(String postcode) throws CustomException {
        return locationRepo.getLocationByPostcode(postcode).orElseThrow(()-> new CustomException("District's postal not exists."));
    }

    public LocationModel bindLocation(LocationRequest locationRequest) throws CustomException {
        try{
            return findLocationByPostcode(locationRequest.getPostcode());
        }catch (Exception e){
            createLocation(locationRequest);
            return findLocationByPostcode(locationRequest.getPostcode());
        }
    }

}
