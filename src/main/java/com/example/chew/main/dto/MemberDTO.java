package com.example.chew.main.dto;

import com.example.chew.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDTO {
    String id;
    String pw;
    String name;
    String phone;
    String birth;

    public MemberEntity entity(){
        return MemberEntity.builder()
                .id(id)
                .pw(pw)
                .name(name)
                .phone(phone)
                .birth(Date.valueOf(birth))
                .build();
    }
}
