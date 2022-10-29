package com.backend.model;

import lombok.*;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;

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
