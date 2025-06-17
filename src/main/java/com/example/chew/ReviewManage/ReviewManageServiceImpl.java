package com.example.chew.ReviewManage;

import com.example.chew.entity.ReviewEntity;
import com.example.chew.Review.ReviewId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReviewManageServiceImpl implements ReviewManageService {

    @Autowired
    ReviewManageRepository reviewManageRepository;

    @Override
    public Page<ReviewEntity> reviewlistin(int page) {
        int size = 7;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ReviewEntity> reviewPage = reviewManageRepository.findAll(pageRequest);

        // store Lazy 초기화 방지
        reviewPage.getContent().forEach(r -> r.getStore().getStorename());

        return reviewPage;
    }

    @Override
    public void deleteReviewById(ReviewId id) {
        reviewManageRepository.deleteById(id);
    }
}