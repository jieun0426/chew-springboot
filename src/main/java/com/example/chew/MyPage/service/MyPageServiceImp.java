package com.example.chew.MyPage.service;

import com.example.chew.JoinLogin.dto.MemberDTO;
import com.example.chew.entity.MemberEntity;
import com.example.chew.MyPage.dto.UpdateMemberDTO;
import com.example.chew.MyPage.repository.MyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MyPageServiceImp implements MyPageService {

    @Autowired
    MyPageRepository myPageRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean checkpassword(String id, String pw) {
        MemberEntity member = myPageRepository.findById(id).orElse(null);
        if (member == null) return false;

        return bCryptPasswordEncoder.matches(pw, member.getPw());
    }

    @Override
    public MemberDTO getid0605() {
        return null;
    }

    @Transactional
    @Override
    public void updateMemberFromUpdateDT(UpdateMemberDTO dto) {
        MemberEntity entity = myPageRepository.findById(dto.getId()).orElse(null);
        if (entity != null) {
            entity.setName(dto.getName());
            entity.setPhone(dto.getPhone());

            if (dto.getBirth() != null && !dto.getBirth().isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthDate = sdf.parse(dto.getBirth());
                    entity.setBirth(birthDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                entity.setBirth(null);
            }

            myPageRepository.save(entity);
        }

    }

    @Override
    public UpdateMemberDTO getLoginMemberForUpdate() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        return myPageRepository.findById(id)
                .map(entity -> {
                    UpdateMemberDTO dto = new UpdateMemberDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    dto.setPhone(entity.getPhone());

                    if (entity.getBirth() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        dto.setBirth(sdf.format(entity.getBirth()));
                    }
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public void deleteMemberAndRelatedData(String id) {
        // 1. 예약 삭제
        myPageRepository.deleteBookingsById(id);

        // 2. 리뷰 삭제
        myPageRepository.deleteReviewsById(id);

        // 3. 좋아용 삭제

        // 4. 회원 삭제
        myPageRepository.deleteById(id);
    }

    @Override
    public Page<Map<String, Object>> getUserBookList(String id, int page) {
        Pageable pageable = PageRequest.of(page, 5); // 한 페이지당 5개씩
        return myPageRepository.findPagedBookingsById(id, pageable);
    }

    @Override
    public Page<Map<String, Object>> getUserReviewList(String id, int page) {
        Pageable pageable = PageRequest.of(page, 5); // 한 페이지당 5개씩
        return myPageRepository.findPagedReviewsById(id, pageable);
    }


}



