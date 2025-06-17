package com.example.chew.MyPage.controller;

import com.example.chew.MyPage.dto.UpdateMemberDTO;
import com.example.chew.MyPage.service.MyPageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class MyPageController {

    @Autowired
    MyPageService myPageService;

    //마이페이지 접속 전 비밀번호 확인창
    @GetMapping("/mypagePwcheck")
    public String mypagepwc1(){
        return "mypage/mypagePwcheck";
    }

    @PostMapping("/mypagepwchecking")
    public String checkPassword(@RequestParam("pw") String pw, Model mo) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();//사용자의 **아이디(또는 사용자명)**를 가져오는 코드

        if (!myPageService.checkpassword(id, pw)) {
            mo.addAttribute("alertMessage", "비밀번호가 틀렸습니다.");
            return "mypage/mypagePwcheck";
        }

        return "redirect:/mypinfo";
    }

    //내정보수정창
    @PostMapping("/myinfosave")
    public String updateMember(@ModelAttribute("updateMemberDTO") @Valid UpdateMemberDTO dto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model mo) {
        if (bindingResult.hasErrors()) {
            mo.addAttribute("updateMemberDTO", dto);
            return "mypage/mypinfo";
        }

        myPageService.updateMemberFromUpdateDT(dto);
        redirectAttributes.addFlashAttribute("alertMessage", "수정이 완료되었습니다.");
        return "redirect:/mypinfo";
    }

    @GetMapping("/mypinfo")
    public String showMemberInfo(Model mo,
                                 @ModelAttribute(value = "alertMessage", binding = false) String alertMessage) {
        UpdateMemberDTO dto = myPageService.getLoginMemberForUpdate();
        mo.addAttribute("updateMemberDTO", dto);

        return "mypage/mypinfo";
    }

    //회원탈퇴창
    @GetMapping("/mypagedel")
    public String mypagedel1(){
        return "mypage/mypagedel";
    }

    @PostMapping("/mypagedelsave")
    public String deleteUser(@RequestParam String pw,
                             RedirectAttributes ra,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        if (myPageService.checkpassword(id, pw)) {
            myPageService.deleteMemberAndRelatedData(id);

            try {
                request.logout(); // ✔ Spring Security 로그아웃 처리
            } catch (ServletException e) {
                e.printStackTrace();
            }

            return "redirect:/loginput?logout"; // 또는 return "redirect:/";
        } else {
            ra.addFlashAttribute("alertMessage", "비밀번호가 틀렸습니다.");
            return "redirect:/mypagedel";
        }
    }


    //마이페이지 예약내역
    @GetMapping("/mypagebook")
    public String mypagebook(Model mo,
                             @RequestParam(defaultValue = "0") int page) {

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        if (id == null || "anonymousUser".equals(id)) {
            return "redirect:/loginput";
        }

        Page<Map<String, Object>> bookPage = myPageService.getUserBookList(id, page);

        int nowpage = bookPage.getNumber(); // 0-based
        int totalPage = bookPage.getTotalPages();
        int pageBlockSize = 5;
        int nowPageBlock = nowpage / pageBlockSize;

        mo.addAttribute("mybook", bookPage.getContent());
        mo.addAttribute("nowpage", nowpage);
        mo.addAttribute("totalPage", totalPage);
        mo.addAttribute("pageBlockSize", pageBlockSize);
        mo.addAttribute("nowPageBlock", nowPageBlock);

        return "mypage/mypagebook";
    }

    //마이페이지 리뷰내역
    @GetMapping("/mypagereview")
    public  String mypagere1(Model mo,
                             @RequestParam(defaultValue = "0") int page){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        if (id == null || "anonymousUser".equals(id)) {
            return "redirect:/loginput";
        }
        Page<Map<String, Object>> reviewPage = myPageService.getUserReviewList(id, page);

        int nowpage = reviewPage.getNumber(); // 0-based
        int totalPage = reviewPage.getTotalPages();
        int pageBlockSize = 5;
        int nowPageBlock = nowpage / pageBlockSize;

        mo.addAttribute("myreviews", reviewPage.getContent());
        mo.addAttribute("nowpage", nowpage);
        mo.addAttribute("totalPage", totalPage);
        mo.addAttribute("pageBlockSize", pageBlockSize);
        mo.addAttribute("nowPageBlock", nowPageBlock);
        return "mypage/mypagereview";
    }


}
