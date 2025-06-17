package com.example.chew.ReviewManage;

import com.example.chew.entity.ReviewEntity;
import com.example.chew.Review.ReviewId;
import org.springframework.data.domain.Page;

public interface ReviewManageService {
    Page<ReviewEntity> reviewlistin(int page);
    void deleteReviewById(ReviewId id);
}