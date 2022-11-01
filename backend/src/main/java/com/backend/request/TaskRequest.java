package com.backend.request;

import lombok.*;

import java.time.ZonedDateTime;

/*
    Purpose:
        - Create class to carry data as input for Task controller or services

    Author:
        - Alex Lim
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequest {
  Long taskId;
  String collectorEmail;
  String assignedByAdminEmail;
  Integer machineId;
  ZonedDateTime assignedTime;
  ZonedDateTime collectedTime;
  ZonedDateTime deliveredTime;
}
