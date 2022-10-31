package com.backend.request;

import lombok.*;


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
