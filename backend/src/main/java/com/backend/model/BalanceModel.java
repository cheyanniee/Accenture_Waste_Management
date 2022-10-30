package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;

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
