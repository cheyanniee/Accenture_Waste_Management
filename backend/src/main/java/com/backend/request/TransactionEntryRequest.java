package com.backend.request;

import lombok.*;

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
