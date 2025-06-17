package com.example.chew.JoinLogin.repository;

import com.example.chew.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,String> {
    @Query(value = "select id from member_3 where name = :name and phone = :phone", nativeQuery = true)
    String findbyid06(@Param("name") String name, @Param("phone") String phone);

    MemberEntity findOneById(String id);

    @Query(value = "select pw from member_3 where id = :id and name = :name", nativeQuery = true)
    String findbypw06(@Param("id") String id,@Param("name") String name);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE MemberEntity m SET m.pw = :pw WHERE m.id = :id")
    int updatePassword(@Param("id") String id, @Param("pw") String pw);

}
