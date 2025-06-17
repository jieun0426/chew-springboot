package com.example.chew.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ReviewDTO {
    private String id;
    private int storecode;
    private String content;
    private Double stars;
    private String title;

}
