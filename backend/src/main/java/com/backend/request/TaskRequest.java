package com.backend.request;

import java.time.ZonedDateTime;

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
public class TaskRequest {
  String collectorEmail;
  String assignedByAdminEmail;
  Integer machineId;
  ZonedDateTime assignedTime;
  ZonedDateTime collectedTime;
  ZonedDateTime deliveredTime;
}
