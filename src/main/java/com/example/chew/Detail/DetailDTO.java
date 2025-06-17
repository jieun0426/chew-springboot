package com.example.chew.Detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class DetailDTO {
    private Integer storecode;
    private String storename;
    private String storeaddress;
    private String storecategory;
    private Integer storelikes;
    private String storearea;
    private String storeimage;
    private Integer parking;
    private Double latitude;
    private Double longitude;
    private String storehours;


    public static DetailDTO fromEntity(DetailEntity entity) {
        return DetailDTO.builder()
                .storecode(entity.getStorecode())
                .storename(entity.getStorename())
                .storeaddress(entity.getStoreaddress())
                .storecategory(entity.getStorecategory())
                .storelikes(entity.getStorelikes())
                .storearea(entity.getStorearea())
                .storeimage(entity.getStoreimage())
                .parking(entity.getParking())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .storehours(entity.getStorehours())
                .build();
    }

}
