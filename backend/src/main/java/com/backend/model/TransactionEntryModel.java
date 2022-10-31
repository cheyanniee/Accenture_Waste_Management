package com.backend.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="transaction_entry")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name="battery_id")
    BatteryModel batteryModel;

    @ManyToOne
    @JoinColumn(name="transaction_id")
    TransactionModel transactionModel;

    Float quantity;

}
