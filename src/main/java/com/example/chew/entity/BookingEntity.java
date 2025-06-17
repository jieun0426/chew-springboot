package com.example.chew.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "booking_3")
@SequenceGenerator(
        name = "bookingseq",
        sequenceName = "booking_seq",
        allocationSize = 1,
        initialValue = 1000
)
public class BookingEntity {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="bookingseq")
    int tablenum;
    @Column
    String id;
    @Column
    int saramsu;
    @Column
    String state;
    @Column
    Date bookingdate;
    @Column
    String bookingtime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storecode", referencedColumnName = "storecode")
    private StoreEntity store;
}
