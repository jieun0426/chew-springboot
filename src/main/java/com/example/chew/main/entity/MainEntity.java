package com.example.chew.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STORE_3")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MainEntity {

    @Id
    @Column(name = "STORECODE")
    private Long storecode;

    @Column(name = "STORENAME")
    private String storename;

    @Column(name = "STOREIMAGE")
    private String storeimage;

    @Column(name = "STOREAREA")
    private String storearea;

    // 쿼리에서 AVG(STARS)로 계산한 결과를 받는 필드
    @Transient// DB에 직접 존재하지 않음
    @Column(name = "STARS")
    private Double stars;

}
