package com.example.chew.membermanage.service;

import com.example.chew.entity.MemberEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberManageService {

    int delete( String id);

    int update(MemberEntity dto);

    Page<MemberEntity> allMemberList(int page);

    List<MemberEntity> searchById(String searchValue);

    List<MemberEntity> searchByName(String searchValue);

    MemberEntity selectOne(String id);
}
