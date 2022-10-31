package com.backend.request;


import com.backend.model.TransactionModel;
import lombok.*;
import java.time.ZonedDateTime;

/*
    Purpose:
        - Object to be used for sending the necessary fields and data for Transaction-related APIs

    Author:
        - Lew Xu Hong
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    Long peopleId;
    Integer machineId;
    Float balanceChange;
    ZonedDateTime dateAndTime;
    TransactionModel.Choose chooseType;
}
