package com.example.chew.Likes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "MEMBER_LIKES", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"MEMBER_ID", "STORECODE"})
})
@IdClass(MemberLikesId.class)
public class LikesEntity {
    @Id
    @Column(name = "member_id")
    String memberId;
    @Id
    @Column(name = "storecode")
    int storecode;
    @Column(name = "LIKED_AT")
    private LocalDateTime likedAt;

    public LikesEntity(String memberId, int storecode) {
        this.memberId = memberId;
        this.storecode = storecode;
        this.likedAt = LocalDateTime.now();
    }

}
