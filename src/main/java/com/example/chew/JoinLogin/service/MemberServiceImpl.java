package com.example.chew.JoinLogin.service;

import com.example.chew.JoinLogin.dto.MemberDTO;
import com.example.chew.entity.MemberEntity;
import com.example.chew.JoinLogin.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void memberinsert(MemberDTO memberDTO) {
        memberDTO.setPw(bCryptPasswordEncoder.encode(memberDTO.getPw()));
        memberRepository.save(memberDTO.toEntity());
    }

    @Override
    public boolean idExists(String id) {
        return memberRepository.findById(id).isPresent();
    }

    @Override
    public String findbyid06(String name, String phone) {
        return memberRepository.findbyid06(name, phone);
    }

    @Override
    public String findbypw06(String id, String name) {
        return memberRepository.findbypw06(id, name);
    }

    @Transactional
    @Override
    public boolean updatePassword(String id, String pw) {
        MemberEntity member = memberRepository.findById(id).orElse(null);
        if (member == null) {
            System.out.println("해당 ID 회원 없음");
            return false;
        }

        // 👉👉 여기에서 로그 출력 추가!
        System.out.println("비밀번호 변경 요청 ID: " + id);
        String encodedPw = bCryptPasswordEncoder.encode(pw);
        System.out.println("암호화된 비밀번호: " + encodedPw);

        int updatedCount = memberRepository.updatePassword(id, encodedPw);
        System.out.println("업데이트된 행 개수: " + updatedCount);

        return updatedCount > 0;
    }


}
