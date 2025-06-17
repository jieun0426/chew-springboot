package com.example.chew.storemanage.repository;

import com.example.chew.entity.ReviewEntity;
import com.example.chew.Review.ReviewId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, ReviewId> {

    @Transactional
    @Modifying
    @Query(value = "delete from review_3 where storecode=:storecode", nativeQuery = true)
    void deleteById(int storecode);
}
