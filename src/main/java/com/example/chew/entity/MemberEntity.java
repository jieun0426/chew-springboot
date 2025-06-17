package com.example.chew.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "member_3")
public class MemberEntity {
    @Id
    @Column
    String id;
    @Column
    String pw;
    @Column
    String name;
    @Column
    String phone;
    @Column
    Date birth;


}
