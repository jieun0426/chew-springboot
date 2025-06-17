package com.example.chew.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "member_likes")
public class MemberLikesEntity {
    @EmbeddedId
    @Column
    MemberLikesId memberLikesId;
    @Column
    Timestamp liked_at;
}
