package com.example.chew.Review;

import com.example.chew.entity.ReviewEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    double getAverageStars(int storecode);

    void saveReview(int storecode, String title, String content, double stars);

    List<ReviewEntity> getReviewsByStorecode(int storecode);

    void deleteReview(String id, int storecode);


    void updateReview(String id, int storecode, String title, String content, int stars);
}
