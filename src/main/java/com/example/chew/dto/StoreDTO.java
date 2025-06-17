package com.example.chew.dto;

import com.example.chew.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StoreDTO {
    Integer storecode;
    String storename;
    String storeaddress;
    String storecategory;
    Integer storelikes;
    String storearea;
    String storeimage;
    Boolean parking;
    Double latitude;
    Double longitude;
    String storehours;

    public StoreDTO(Integer storecode, String storename, String storeaddress, String storecategory, String storeimage, String storearea) {
        this.storecode = storecode;
        this.storename = storename;
        this.storeaddress = storeaddress;
        this.storecategory = storecategory;
        this.storeimage = storeimage;
        this.storearea = storearea;
    }

    public StoreEntity entity(){
        return StoreEntity.builder()
                .storecode(storecode)
                .storename(storename)
                .storeaddress(storeaddress)
                .storecategory(storecategory)
                .storelikes(storelikes)
                .storearea(storearea)
                .storeimage(storeimage)
                .parking(parking?1:0)
                .latitude(latitude)
                .longitude(longitude)
                .storehours(storehours)
                .build();
    }
}
