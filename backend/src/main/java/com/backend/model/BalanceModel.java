package com.backend.model;

import lombok.*;
import javax.persistence.*;

/*
    Purpose:
        - Entity to match with table in database (Balance)

    Author:
        - Lew Xu Hong
*/
@Entity
@Table(name="balance")
@Builder
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //foreign key with people
    @OneToOne
    @JoinColumn(name="people_id")
    PeopleModel peopleModel;
    
    Float currentBalance;

}
