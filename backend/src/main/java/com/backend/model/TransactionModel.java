package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(
        name="pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class TransactionModel {

    public enum Choose {
        recycle, exchange
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //foreign key with people and machine
    @OneToOne
    @JoinColumn(name="people_id")
    PeopleModel peopleModel;

    @OneToOne
    @JoinColumn(name="machine_id")
    MachineModel machineModel;

//    @OneToMany
//    @JoinColumn(name="transaction_entry_id")
//    List<TransactionEntryModel> transactionEntry = new ArrayList<>();

    Float balanceChange;

    ZonedDateTime dateAndTime;

    @Enumerated(EnumType.STRING)
    @Type( type = "pgsql_enum" )
    TransactionModel.Choose choose;

}
