package com.backend.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.ZonedDateTime;
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

    Float balanceChange;

    ZonedDateTime dateAndTime;

    @Enumerated(EnumType.STRING)
    @Type( type = "pgsql_enum" )
    TransactionModel.Choose choose;

}
