package com.example.chew.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MainDTO {
    private Long storecode;
    private String storename;
    private String storeimage;
    private String storearea;
    private Double stars;


}
