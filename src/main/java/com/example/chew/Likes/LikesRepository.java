package com.example.chew.Likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, MemberLikesId> {


    boolean existsByMemberIdAndStorecode(String memberId, Integer storecode);

    void deleteByMemberIdAndStorecode(String memberId, Integer storecode);
}
