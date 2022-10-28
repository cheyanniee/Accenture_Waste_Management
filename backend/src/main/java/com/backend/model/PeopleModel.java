package com.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name="people")
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
public class PeopleModel {

    public enum Role {
        user,collector,admin
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //foreign key with location
    @OneToOne
    @JoinColumn(name="location_id")
    LocationModel locationModel;

    //    @Column(name = "last_name")
    String firstName;

    //    @Column(name = "first_name")
    String lastName;
    String email;
    @JsonIgnore
    String password;
    Integer phoneNumber;
    String dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Type( type = "pgsql_enum" )
    Role role;

    String officialId;
    String token;

    @Column(name = "last_login")
    ZonedDateTime lastLogin;

}
