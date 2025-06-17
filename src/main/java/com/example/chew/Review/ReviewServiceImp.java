package com.example.chew.Review;

import com.example.chew.JoinLogin.repository.MemberRepository;
import com.example.chew.entity.MemberEntity;
import com.example.chew.entity.ReviewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImp implements ReviewService {

    @Autowired
   ReviewRepository_detail reviewRepository;
    @Autowired
    MemberRepository memberRepository;

    @Override
    public double getAverageStars(int storecode) {
        Double avg = reviewRepository.findAverageStarByStoreCode(storecode);
        return avg != null ? avg : 0.0;
    }

    @Override
    public void saveReview(int storecode, String title, String content, double stars) {
        // 로그인된 사용자 ID 가져오기
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();

        // 사용자 엔티티 조회
        MemberEntity member = memberRepository.findById(loginId)
                .orElseThrow(() -> new RuntimeException("로그인한 회원 정보를 찾을 수 없습니다."));

        // 리뷰 저장
        ReviewEntity review = ReviewEntity.builder()
                .id(new ReviewId(loginId,storecode))
                .title(title)
                .content(content)
                .stars(stars)
                .build();
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewEntity> getReviewsByStorecode(int storecode) {
        return reviewRepository.findByStorecode(storecode);
    }

    @Override
    public void deleteReview(String id, int storecode) {
        reviewRepository.deleteByIdAndStorecode(id, storecode);
    }

    @Override
    public void updateReview(String id, int storecode, String title, String content, int stars) {
        reviewRepository.updateReviewByIdAndStorecode(title, content, stars, id, storecode);
    }


}
