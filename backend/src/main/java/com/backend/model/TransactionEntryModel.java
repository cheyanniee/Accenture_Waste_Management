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

    @OneToOne
    @JoinColumn(name="battery_id")
    BatteryModel batteryModel;

    @OneToOne
    @JoinColumn(name = "rate_id")
    RateModel rateModel;

    @ManyToOne
    @JoinColumn(name="transaction_id")
    TransactionModel transactionModel;

    Float quantity;

}
