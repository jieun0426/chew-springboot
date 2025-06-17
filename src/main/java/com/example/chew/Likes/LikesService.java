package com.example.chew.Likes;

import org.springframework.stereotype.Service;

@Service
public interface LikesService {


    boolean toggleLike(String memberId, Integer storecode);

    int getStoreLikes(Integer storecode);

    boolean isLiked(String memberId, Integer storecode);
}
