package com.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name="transaction_entry")
@Builder
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

//    @OneToOne
//    @JoinColumn(name="batteries_id")
//    BatteriesModel batteriesModel;

    Integer quantity;

}
