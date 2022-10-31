package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.model.PeopleModel;
import com.backend.request.PeopleRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.PeopleService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    Purpose:
        - APIs for PeopleModel-related operations
        - Allow People to register, login, and logout
        - Allow People to retrieve people entry data from people table by token and officialID
        - Allow People to update people entry data by token
        - Allow People (Admin) to register new People with collector and admin roles
        - (please add on here for the other functions)

    Restriction:
        - Only those with ROLES.Admin will be able to register new People with collector and admin roles,
        and view list of all PeopleModels.

    Endpoints:
        - dev/v1/people/listall
        - dev/v1/people/find
        - dev/v1/people/update
        - dev/v1/people/login
        - dev/v1/people/getinfo
        - dev/v1/people/logout

    Author:
        - Liu Fang,
        - Lew Xu Hong (endpoints: register, register/collector, register/admin
                        services: createUser, createrCollector, createAdmin)
*/

@RestController
@RequestMapping("dev/v1/people")
public class PeopleController {

    @Autowired
    PeopleService peopleService;

    @GetMapping("listall")
    public ResponseEntity<?> listPeople(@RequestHeader String token) {
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(peopleService.getIdByToken(token));
            if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){
                return ResponseEntity.badRequest().body(new GeneralResponse("User does not have rights to acccess page"));
            }else{
                return ResponseEntity.ok(peopleService.listPeople());
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("register")//Xu Hong
    public ResponseEntity<?> registerPeople(@RequestBody PeopleRequest peopleRequest) {
        try {
            peopleService.createUser(peopleRequest);
            return ResponseEntity.ok(new GeneralResponse("Register successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("register/collector")//Xu Hong
    public ResponseEntity<?> registerCollector(@RequestBody PeopleRequest peopleRequest, @RequestHeader String token) {
        //verify if requester is an admin
        Long id = peopleService.getIdByToken(token);
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(id);
            if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){
                return ResponseEntity.badRequest().body(new GeneralResponse("User is not admin"));
            }else{
                peopleService.createCollector(peopleRequest);
                return ResponseEntity.ok(new GeneralResponse("Register collector successfully!"));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("register/admin")//Xu Hong
    public ResponseEntity<?> registerAdmin(@RequestBody PeopleRequest peopleRequest, @RequestHeader String token) {
        //verify if requester is an admin
        Long id = peopleService.getIdByToken(token);
        try{
            PeopleModel peopleModel = peopleService.getPeopleById(id);
            if (!peopleModel.getRole().equals(PeopleModel.Role.admin)){
                return ResponseEntity.badRequest().body(new GeneralResponse("User is not admin"));
            }else{
                peopleService.createAdmin(peopleRequest);
                return ResponseEntity.ok(new GeneralResponse("Register admin successfully!"));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }

    }

    //LF
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody PeopleRequest peopleRequest) {
        try {
            return ResponseEntity
                    .ok(peopleService.loginValidate(peopleRequest.getEmail(), peopleRequest.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @GetMapping("getinfo")
    public ResponseEntity<?> getPeople(@RequestHeader String token) throws Exception {
        PeopleModel people = peopleService.getPeopleById(peopleService.getIdByToken(token));
        return ResponseEntity.ok(people);
    }

    @PostMapping("update")
    public ResponseEntity<?> updatePeople(@RequestBody PeopleRequest peopleRequest, @RequestHeader String token)
            throws CustomException {
        peopleService.updatePeople(peopleRequest, token);
        return ResponseEntity.ok(new GeneralResponse(
                "User " + peopleRequest.getFirstName() + " " + peopleRequest.getLastName() + " update successfully!"));

    }

    @GetMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader String token) throws Exception {
        peopleService.logout(peopleService.getIdByToken(token));
        return ResponseEntity.ok(new GeneralResponse("Logout successfully!"));
    }

    @GetMapping("find")//retrieve people data based on officialId
    public ResponseEntity<?> findPeopleByOfficialId(@RequestParam String officialId) throws CustomException {
        return ResponseEntity.ok(peopleService.findPeopleByOfficialId(officialId));
    }

}
