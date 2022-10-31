package com.backend.request;

import lombok.*;

/*
    Purpose:
        - Object to be used for sending the necessary fields and data for Storage-related APIs

    Author:
        - Lew Xu Hong
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageRequest {

    Integer machineId;
    Integer qtyAA;
    Integer qtyAAA;
}
