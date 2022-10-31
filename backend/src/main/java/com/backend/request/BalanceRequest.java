package com.backend.request;

import lombok.*;

/*
    Purpose:
        - Object to be used for sending the necessary fields and data for Balance-related APIs

    Author:
        - Lew Xu Hong
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceRequest {

    Long peopleId;
    Float currentBalance;
}
