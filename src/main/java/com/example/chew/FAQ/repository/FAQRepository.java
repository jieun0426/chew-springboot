package com.example.chew.FAQ.repository;

import com.example.chew.entity.FAQEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQEntity, Integer> {
    Page<FAQEntity> findById(String id, Pageable pageable);

    Page<FAQEntity> findByStep(int i, Pageable pageable);

    @Transactional
    @Query(value = "select num, id, NVL(title, '제목없음') as title, content, wdate, " +
            "NVL(state, '답변') as state, groups, step, NVL(secret, 0) as secret from FAQ_3 " +
            "where num=:qnum", nativeQuery = true)
    FAQEntity findQuestionByNum(String qnum);

    @Transactional
    @Query(value = "select num, id, NVL(title, '제목없음') as title, content, wdate, " +
            "NVL(state, '답변') as state, groups, step, NVL(secret, 0) as secret from FAQ_3 " +
            "where groups=:qnum and step=1", nativeQuery = true)
    FAQEntity findAnswerByNum(String qnum);

    @Transactional
    @Modifying
    @Query(value = "update FAQ_3 set state='답변완료' where num=:num", nativeQuery = true)
    void updateQuestionState(int num);

    @Transactional
    @Modifying
    @Query(value = "update FAQ_3 set content=:content, id=:id where groups=:groups and step=1", nativeQuery = true)
    void updateAnswer(@Param("content") String content, @Param("id") String id, @Param("groups") int groups);
}
