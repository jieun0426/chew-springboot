package com.example.chew.JoinLogin.controller;

import com.example.chew.JoinLogin.dto.PasswordUpdateDTO;
import com.example.chew.JoinLogin.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IdPwFindController {

    @Autowired
    MemberService memberService;

    //아이디찾기창
    @GetMapping("/login_Idfind")
    public String loginPage() {
        return "/login/login_Idfind";
    }

    @PostMapping("/findIdCheck")
    public String idfind(@RequestParam("name") String name,
                         @RequestParam("phone") String phone,
                         Model mo) {
        String findId = memberService.findbyid06(name, phone);
        mo.addAttribute("findId", findId);
        return "/login/findIdresult";
    }

    //비밀번호찾기창
    @GetMapping("/login_Pwfind")
    public String loginPage2() {
        return "/login/login_Pwfind";
    }

    @PostMapping("/findPwCheck")
    public String pwfind(@RequestParam("id") String id,
                         @RequestParam("name") String name,
                         Model mo) {
        boolean userExists = memberService.findbypw06(id, name) != null;

        mo.addAttribute("userExists", userExists);

        if (userExists) {
            PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
            passwordUpdateDTO.setId(id);
            mo.addAttribute("passwordUpdateDTO", passwordUpdateDTO);
        }

        return "/login/findPwresult";
    }

    //비밀번호 변경창
    @PostMapping("/updatepw")
    public String updatePassword(@ModelAttribute("passwordUpdateDTO") @Valid PasswordUpdateDTO passwordUpdateDTO,
                                 BindingResult bindingResult,
                                 Model mo,
                                 RedirectAttributes redirectAttributes) {

        if (!passwordUpdateDTO.getPw().equals(passwordUpdateDTO.getPwConfirm())) {
            bindingResult.rejectValue("pwConfirm", "error.pwConfirm", "비밀번호가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            mo.addAttribute("userExists", true);
            return "/login/findPwresult";
        }

        boolean success = memberService.updatePassword(passwordUpdateDTO.getId(), passwordUpdateDTO.getPw());

        if (success) {
            redirectAttributes.addFlashAttribute("msg", "비밀번호가 성공적으로 변경되었습니다.");
            return "redirect:/loginput";
        } else {
            mo.addAttribute("msg", "비밀번호 변경에 실패했습니다.");
            mo.addAttribute("userExists", true);
            return "/login/findPwresult";
        }
    }


}
