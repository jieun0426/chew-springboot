package com.example.chew.JoinLogin.dto;

import com.example.chew.entity.MemberEntity;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDTO {

    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "아이디는 4~12자의 영문자 또는 숫자만 가능합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "비밀번호는 4~15자의 영문자 또는 숫자만 가능합니다.")
    private String pw;

    @NotBlank(message = "이름은 필수입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{1,4}$", message = "이름은 1~4자의 한글 또는 영문자만 가능합니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식은 010-1234-5678입니다.")
    private String phone;

    private String birth;

    public MemberEntity toEntity() {
        Date birthDate = null;
        if (birth != null && !birth.trim().isEmpty()) {
            birthDate = Date.valueOf(birth);
        }

        return MemberEntity.builder()
                .id(id)
                .pw(pw)
                .name(name)
                .phone(phone)
                .birth(birthDate)
                .build();
    }
}
