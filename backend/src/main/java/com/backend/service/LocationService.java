package com.backend.service;

import com.backend.model.LocationModel;
import com.backend.repo.LocationRepo;
import com.backend.request.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    LocationRepo locationRepo;

    public List<LocationModel> listAllLocation() {
        return locationRepo.findAll();
    }

    public void createLocation(LocationRequest locationRequest){
        LocationModel locationNew = LocationModel.builder()
                .address(locationRequest.getAddress())
                .postcode(locationRequest.getPostcode())
                .areaName(locationRequest.getAreaName())
                .regionName(locationRequest.getRegionName())
                .build();

        locationRepo.save(locationNew);
    }

}
