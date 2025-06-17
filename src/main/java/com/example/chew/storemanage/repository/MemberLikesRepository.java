package com.example.chew.storemanage.repository;

import com.example.chew.entity.MemberLikesEntity;
import com.example.chew.entity.MemberLikesId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberLikesRepository extends JpaRepository<MemberLikesEntity, MemberLikesId> {

    @Transactional
    @Modifying
    @Query(value = "delete from member_likes where storecode=:storecode", nativeQuery = true)
    void deleteById(int storecode);
}
