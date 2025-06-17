package com.example.chew.membermanage.repository;

import com.example.chew.entity.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MemberManageRepository extends JpaRepository<MemberEntity, String> {
    List<MemberEntity> findByIdContaining(String searchValue);

    List<MemberEntity> findByNameContaining(String searchValue);

    @Transactional
    @Modifying
    @Query(value = "update member_3 set name=:name, phone=:phone, birth=:birth where id=:id", nativeQuery = true)
    void updateMember(String id, String name, String phone, Date birth);
}
