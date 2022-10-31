package com.backend.request;


import com.backend.model.TransactionModel;
import lombok.*;
import java.time.ZonedDateTime;


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
