package com.backend.request;

import com.backend.model.PeopleModel;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceRequest {

    //PeopleModel peopleModel;
    Long peopleId;
    Float currentBalance;
}
