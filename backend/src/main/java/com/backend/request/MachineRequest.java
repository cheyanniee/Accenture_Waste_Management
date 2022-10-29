package com.backend.request;

import com.backend.model.MachineModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MachineRequest {
  String name;
  Float currentLoad;
  Float capacity;
  MachineModel.Status status;
  Long locationId;
  String address;
  String postcode;
  String unitNumber;
}
