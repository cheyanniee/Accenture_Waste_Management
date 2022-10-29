package com.backend.request;

import com.backend.model.PeopleModel;
import com.backend.model.TransactionEntryModel;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionRequest {

    Long id;
    PeopleModel peopleModel;
//    MachineModel machineModel;

    List<TransactionEntryModel> transactionEntry = new ArrayList<>();
    Integer balanceChange;
    ZonedDateTime dateAndTime;
}
