package com.example.chew.ReviewManage;

import com.example.chew.entity.ReviewEntity;
import com.example.chew.Review.ReviewId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewManageRepository extends JpaRepository<ReviewEntity, ReviewId> {
    // EntityGraph 제거
    Page<ReviewEntity> findAll(Pageable pageable);
}