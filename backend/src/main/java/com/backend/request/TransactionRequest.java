package com.backend.request;

import com.backend.model.MachineModel;
import com.backend.model.PeopleModel;
import com.backend.model.TransactionEntryModel;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    PeopleModel peopleModel;
    MachineModel machineModel;
   // Long peopleId;
   // Long machineId;
    List<TransactionEntryModel> transactionEntry = new ArrayList<>();
    Integer balanceChange;
    ZonedDateTime dateAndTime;
}
