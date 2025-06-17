package com.example.chew.JoinLogin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasswordUpdateDTO {
    @NotBlank(message = "아이디는 필수입니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "비밀번호는 4~15자의 영문자 또는 숫자만 가능합니다.")
    private String pw;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String pwConfirm;
}

