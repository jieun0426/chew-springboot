package com.example.chew.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "store_3")
public class StoreEntity {
    @Id
    @Column
    Integer storecode;
    @Column
    String storename;
    @Column
    String storeaddress;
    @Column
    String storecategory;
    @Column
    Integer storelikes;
    @Column
    String storearea;
    @Column
    String storeimage;
    @Column
    Integer parking;
    @Column
    Double latitude;
    @Column
    Double longitude;
    @Column
    String storehours;
}
