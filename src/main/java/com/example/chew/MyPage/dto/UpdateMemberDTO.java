package com.example.chew.MyPage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateMemberDTO {

    @NotBlank(message = "아이디는 필수입니다.")
    private String id;

    @NotBlank(message = "이름은 필수입니다.")
    @Pattern(regexp = "^[^0-9]{1,4}$", message = "이름은 숫자를 포함할 수 없으며, 최대 4자입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식은 010-1234-5678입니다.")
    private String phone;

    private String birth; // yyyy-MM-dd
}

