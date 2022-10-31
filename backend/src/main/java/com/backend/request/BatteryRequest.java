package com.backend.request;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatteryRequest {

    Integer id;
    String type;
    Float recyclePoint;
    Float exchangePoint;
    ZonedDateTime lastUpdate;
}
