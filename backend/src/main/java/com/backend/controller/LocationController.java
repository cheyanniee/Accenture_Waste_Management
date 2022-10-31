package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.request.LocationRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.DistrictService;
import com.backend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Purpose:
//    - Create URLs to register new location
//    - Create URLs to retrieve district object from district table based on postcode
//    - List all forgotPassword entries in forgot_password table of DB for testing purpose
//
//Restrictions:
//    - Nil. User do not need token to send OTP and reset password.
//    - Listall URL is not open to public, as it is for testing purpose. It needs token to access.
//
//Endpoints:
//      - /dev/v1/forgotpassword/listall
//      - /dev/v1/forgotpassword/sendotp
//      - /dev/v1/forgotpassword/reset
//
//Author:
//    - Liu Fang

@RestController
@RequestMapping("dev/v1/location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @Autowired
    DistrictService districtService;

    @GetMapping("listall")
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(locationService.listAllLocation());
    }

    @PostMapping("register")
    public ResponseEntity<?> registerLocation(@RequestBody LocationRequest locationRequest) throws CustomException {
        locationService.createLocation(locationRequest);
        return ResponseEntity.ok(new GeneralResponse("New Location Registered!"));
    }
    //register new location url

    @GetMapping("getdistrict")
    public ResponseEntity<?> getDistrictByPostcode(@RequestParam String postcode) throws CustomException {
        String postalSector = postcode.substring(0, 2);
        return ResponseEntity.ok(districtService.findDistrictByPostalSector(postalSector));
    }
    //get District object by postcode
}
