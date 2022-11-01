package com.backend.request;

import com.backend.model.MachineModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
    Purpose:
        - Create class to carry data as input for Machine controller or services

    Author:
        - Liu Fang
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MachineRequest {
  int machineId;
  String name;
  Float currentLoad;
  Float capacity;
  MachineModel.Status status;
  Long locationId;
  String address;
  String postcode;
  String unitNumber;
}
