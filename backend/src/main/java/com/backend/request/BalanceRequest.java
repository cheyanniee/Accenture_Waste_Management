package com.backend.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceRequest {

    Long peopleId;
    Float currentBalance;
}
