package com.backend.request;

import com.backend.model.MachineModel;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageRequest {

    //MachineModel machineModel;
    Integer machineId;
    Integer qtyAA;
    Integer qtyAAA;
}
