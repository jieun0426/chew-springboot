package com.example.chew.dto;

import com.example.chew.entity.StoreImageEntity;
import com.example.chew.entity.StoreImageId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreImageDTO {
    int storecode;
    String image_filename;

    public StoreImageEntity entity(){
        return StoreImageEntity.builder()
                .id(new StoreImageId(storecode,image_filename))
                .build();
    }
}
