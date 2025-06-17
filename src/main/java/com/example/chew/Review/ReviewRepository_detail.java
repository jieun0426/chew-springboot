package com.example.chew.Review;

import com.example.chew.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewRepository_detail extends JpaRepository<ReviewEntity, ReviewId> {

    @Query(value = "SELECT ROUND(AVG(stars), 1) FROM REVIEW_3 WHERE storecode = :storecode", nativeQuery = true)
    Double findAverageStarByStoreCode(int storecode);

    @Query(value = "select * from review_3 where storecode = :storecode",nativeQuery = true)
    List<ReviewEntity> findByStorecode(int storecode);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Review_3 r WHERE r.id = :id AND r.storecode = :storecode",nativeQuery = true)
    void deleteByIdAndStorecode(@Param("id") String id, @Param("storecode") int storecode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Review_3 r SET r.title = :title, r.content = :content, r.stars = :stars WHERE r.id = :id AND r.storecode = :storecode",nativeQuery = true)
    void updateReviewByIdAndStorecode(@Param("title") String title,
                                      @Param("content") String content,
                                      @Param("stars") int stars,
                                      @Param("id") String id,
                                      @Param("storecode") int storecode);

}
