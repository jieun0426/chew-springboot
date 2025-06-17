package com.example.chew.MyPage.service;

import com.example.chew.JoinLogin.dto.MemberDTO;
import com.example.chew.MyPage.dto.UpdateMemberDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface MyPageService {
    boolean checkpassword(String id, String pw);

    MemberDTO getid0605();


    void updateMemberFromUpdateDT(@Valid UpdateMemberDTO dto);

    UpdateMemberDTO getLoginMemberForUpdate();

    void deleteMemberAndRelatedData(String id);

    Page<Map<String, Object>> getUserBookList(String id, int page);

    Page<Map<String, Object>> getUserReviewList(String id, int page);
}
