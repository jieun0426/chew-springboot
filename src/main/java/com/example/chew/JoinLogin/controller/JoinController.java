package com.example.chew.JoinLogin.controller;

import com.example.chew.JoinLogin.dto.MemberDTO;
import com.example.chew.JoinLogin.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class JoinController {

    @Autowired
    private MemberService memberService;

    //회원가입페이지
    @GetMapping("/joinput")
    public String memberForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "/login/joinput";
    }

    @PostMapping("/joinsave")
    public String join(
            @Valid @ModelAttribute("memberDTO") MemberDTO memberDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        log.info("입력된 이름: {}", memberDTO.getName());

        // 중복 ID 체크
        if (memberService.idExists(memberDTO.getId())) {
            bindingResult.rejectValue("id", "duplicate", "이미 사용 중인 ID입니다.");
        }

        // 유효성 검증 실패 시
        if (bindingResult.hasErrors()) {
            log.warn("회원가입 유효성 오류: {}", bindingResult);
            return "/login/joinput";
        }

        log.info("회원가입 정보: {}", memberDTO);

        memberService.memberinsert(memberDTO);
        redirectAttributes.addFlashAttribute("message", "회원가입을 환영합니다!");

        return "redirect:/";
    }

    //아이디 중복 검사
    @PostMapping("/logcheck")
    @ResponseBody
    public String idCheck(@RequestParam("id") String id) {
        log.info("중복 체크 요청 ID: {}", id);
        return memberService.idExists(id) ? "duplicate" : "ok";
    }

    //에러페이지
    @GetMapping("/errorpage")
    public String errorPage() {
        return "errorpage";
    }


    //로그인페이지
    @GetMapping("/loginput")
    public String loginPage() {
        return "/login/loginput";
    }


}
