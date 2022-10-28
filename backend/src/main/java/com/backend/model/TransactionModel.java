package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="transaction")
@Builder
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //foreign key with people and machine
    @OneToOne
    @JoinColumn(name="location_id")
    PeopleModel peopleModel;

//    @OneToOne
//    @JoinColumn(name="machine_id")
//    MachineModel machineModel;

    @OneToMany
    @JoinColumn(name="transaction_entry_id")
    List<TransactionEntryModel> transactionEntry = new ArrayList<>();

    Integer balanceChange;

    ZonedDateTime dateAndTime;

}
