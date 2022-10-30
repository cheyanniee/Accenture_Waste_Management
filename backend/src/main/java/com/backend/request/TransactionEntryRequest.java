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
 //   String rateType;

    //CHANGE TABLE COLUMN TO FLOAT. can be used for recycle or exchange - just make sure exchange doesn't allow .5 or something
    Float quantity;



}
