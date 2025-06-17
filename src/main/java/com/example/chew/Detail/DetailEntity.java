package com.example.chew.Detail;

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
public class DetailEntity {
    @Id
    @Column(name = "STORECODE", nullable = false)
    private Integer storecode;

    @Column(name = "STORENAME", length = 30)
    private String storename;

    @Column(name = "STOREADDRESS", length = 100)
    private String storeaddress;

    @Column(name = "STORECATEGORY", length = 20)
    private String storecategory;

    @Column(name = "STORELIKES")
    private Integer storelikes;

    @Column(name = "STOREAREA", length = 20)
    private String storearea;

    @Column(name = "STOREIMAGE", length = 200)
    private String storeimage;

    @Column(name = "PARKING", nullable = false)
    private Integer parking;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "STOREHOURS", length = 100)
    private String storehours;

    public DetailDTO toDTO() {
        return DetailDTO.builder()
                .storecode(storecode)
                .storename(storename)
                .storeaddress(storeaddress)
                .storecategory(storecategory)
                .storelikes(storelikes)
                .storearea(storearea)
                .storeimage(storeimage)
                .parking(parking)
                .latitude(latitude)
                .longitude(longitude)
                .storehours(storehours)
                .build();
    }

}
