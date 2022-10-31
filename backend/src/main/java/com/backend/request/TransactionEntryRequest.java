package com.backend.request;

import lombok.*;

/*
    Purpose:
        - Object to be used for sending the necessary fields and data for TransactionEntry-related APIs

    Author:
        - Lew Xu Hong
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntryRequest {

    Long transactionId;
    String batteryType;
    Float quantity;

}
