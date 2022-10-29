package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.model.PeopleModel;
import com.backend.request.BalanceRequest;
import com.backend.request.PeopleRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.BalanceService;
import com.backend.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev/v1/balance")
public class BalanceController {

    @Autowired
    BalanceService balanceService;

    @Autowired
    PeopleService peopleService;

    @GetMapping("listall")
    public ResponseEntity<?> listBalance() {
        return ResponseEntity.ok(balanceService.listBalance());
    }

    @GetMapping("find")
    public ResponseEntity<?> listBalanceByPeopleId(@RequestBody BalanceRequest balanceRequest)  {

        try{
            PeopleModel peopleModel = peopleService.getPeopleById(balanceRequest.getPeopleId());
            return ResponseEntity.ok(balanceService.getBalanceByPeopleId(peopleModel));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new GeneralResponse(e.getMessage()));
        }
    }



}
