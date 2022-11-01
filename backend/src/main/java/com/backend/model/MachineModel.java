package com.backend.model;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

/*
Purpose:
    - Create data model to match with machine table in DB.

Author:
    - Alex Lim
 */

@Entity
@Table(name = "machine")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)

public class MachineModel {

    public enum Status {
        FAULTY, NORMAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    Float currentLoad;
    Float capacity;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    Status status;

    String unitNumber;

    @OneToOne
    @JoinColumn(name = "location_id")
    LocationModel machinelocation;

}
