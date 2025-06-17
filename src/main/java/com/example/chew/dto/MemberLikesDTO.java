package com.example.chew.dto;

import com.example.chew.entity.MemberLikesEntity;
import com.example.chew.entity.MemberLikesId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberLikesDTO {
    String id;
    int storecode;
    Timestamp liked_at;

    public MemberLikesEntity entity(){
        return MemberLikesEntity.builder()
                .memberLikesId(new MemberLikesId(id,storecode))
                .liked_at(liked_at)
                .build();
    }
}
