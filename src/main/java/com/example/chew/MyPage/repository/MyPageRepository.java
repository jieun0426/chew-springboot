package com.example.chew.MyPage.repository;


import com.example.chew.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface MyPageRepository extends JpaRepository<MemberEntity,String> {
    @Modifying
    @Transactional
    @Query(value = "delete from booking_3 where id = :id", nativeQuery = true)
    void deleteBookingsById(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = "delete from review_3 where id = :id", nativeQuery = true)
    void deleteReviewsById(@Param("id") String id);


    @Query(value = """
            SELECT * FROM (
                SELECT inner_query.*, ROWNUM rnum FROM (
                    SELECT b.saramsu, b.state, b.bookingdate, b.bookingtime,
                           s.storename, s.storecode
                    FROM booking_3 b
                    JOIN store_3 s ON b.storecode = s.storecode
                    WHERE b.id = :id
                    ORDER BY b.bookingdate DESC
                ) inner_query
                WHERE ROWNUM <= :#{#pageable.offset + #pageable.pageSize}
            )
            WHERE rnum > :#{#pageable.offset}
            """,
            countQuery = """
                    SELECT COUNT(*) 
                    FROM booking_3 b
                    JOIN store_3 s ON b.storecode = s.storecode
                    WHERE b.id = :id
                    """,
            nativeQuery = true)
    Page<Map<String, Object>> findPagedBookingsById(@Param("id") String id, Pageable pageable);


    @Query(value = """
            SELECT * FROM (
                SELECT inner_query.*, ROWNUM rnum FROM (
                    SELECT r.content, r.stars, r.title,
                           s.storename, s.storecode
                    FROM review_3 r
                    JOIN store_3 s ON r.storecode = s.storecode
                    WHERE r.id = :id
                    ORDER BY r.id DESC
                ) inner_query
                WHERE ROWNUM <= :#{#pageable.offset + #pageable.pageSize}
            )
            WHERE rnum > :#{#pageable.offset}
            """,
            countQuery = """
                    SELECT COUNT(*) 
                    FROM review_3 r
                    JOIN store_3 s ON r.storecode = s.storecode
                    WHERE r.id = :id
                    """,
            nativeQuery = true)
    Page<Map<String, Object>> findPagedReviewsById(String id, Pageable pageable);
}

