package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.model.PeopleModel;
import com.backend.request.PeopleRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dev/v1/people")
public class PeopleController {

    @Autowired
    PeopleService peopleService;

    @GetMapping("listall")
    public ResponseEntity<?> listPeople() {
        return ResponseEntity.ok(peopleService.listPeople());
    }

    @PostMapping("register")
    public ResponseEntity<?> registerPeople(@RequestBody PeopleRequest peopleRequest) {
        try {
            peopleService.createUser(peopleRequest);
            return ResponseEntity.ok(new GeneralResponse("Register successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody PeopleRequest peopleRequest) {
        try {
            return ResponseEntity.ok(peopleService.loginValidate(peopleRequest.getEmail(), peopleRequest.getPassword()));
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
    public ResponseEntity<?> updatePeople(@RequestBody PeopleRequest peopleRequest, @RequestHeader String token) throws CustomException {
        peopleService.updatePeople(peopleRequest, token);
        return ResponseEntity.ok(new GeneralResponse("User " + peopleRequest.getFirstName() + " " + peopleRequest.getLastName() + " update successfully!"));

    }

    @GetMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader String token) throws Exception {
        peopleService.logout(peopleService.getIdByToken(token));
        return ResponseEntity.ok(new GeneralResponse("Logout successfully!"));
    }

    @GetMapping("find")
    public ResponseEntity<?> findPeopleByOfficialId(@RequestParam String officialId) throws CustomException {
        return ResponseEntity.ok(peopleService.findPeopleByOfficialId(officialId));
    }

}
