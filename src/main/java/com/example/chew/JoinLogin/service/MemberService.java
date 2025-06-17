package com.example.chew.JoinLogin.service;

import com.example.chew.JoinLogin.dto.MemberDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public interface MemberService {
    void memberinsert(@Valid MemberDTO memberDTO);

    boolean idExists(String id);

    String findbyid06(String name, String phone);

    String findbypw06(String id, String name);

    boolean updatePassword(@NotBlank(message = "아이디는 필수입니다.") @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "아이디는 4~12자의 영문자 또는 숫자만 가능합니다.") String id, @NotBlank(message = "비밀번호는 필수입니다.") @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "비밀번호는 4~15자의 영문자 또는 숫자만 가능합니다.") String pw);
}
