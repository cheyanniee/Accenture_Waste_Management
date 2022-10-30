package com.backend.request;

import com.backend.model.TransactionModel;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfirmTransactionRequest {

    // PeopleModel peopleModel;
    // MachineModel machineModel;
    Long transactionId;
    Long peopleId;
    Integer machineId;
    // List<TransactionEntryModel> transactionEntry = new ArrayList<>();
    Float balanceChange;
    ZonedDateTime dateAndTime;
    TransactionModel.Choose chooseType;
}
