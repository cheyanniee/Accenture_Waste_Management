package com.backend.request;

import com.backend.model.PeopleModel;

import javax.persistence.*;

public class BalanceRequest {

    Long id;
    PeopleModel peopleModel;
    Integer currentBalance;
}
