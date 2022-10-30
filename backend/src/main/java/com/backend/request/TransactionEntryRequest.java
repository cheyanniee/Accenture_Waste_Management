package com.backend.request;

import com.backend.model.BatteryModel;
import com.backend.model.TransactionModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntryRequest {

    //TransactionModel transactionModel;
    Long transactionId;
    String batteryType;
    Integer quantity;

}
