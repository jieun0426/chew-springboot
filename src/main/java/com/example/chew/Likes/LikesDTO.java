package com.example.chew.Likes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class LikesDTO {
    String memberId;
    int storecode;
    LocalDateTime likedAt;
}
