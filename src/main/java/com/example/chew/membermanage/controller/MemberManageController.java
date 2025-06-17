package com.example.chew.membermanage.controller;

import com.example.chew.JoinLogin.dto.MemberDTO;
import com.example.chew.entity.MemberEntity;
import com.example.chew.membermanage.service.MemberManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MemberManageController {
    @Autowired
    MemberManageService memberManageService;

    @RequestMapping(value = "/members", method = RequestMethod.GET)
    public String memberList(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        try {
            Page<MemberEntity> list = memberManageService.allMemberList(page);

            int totalPage = list.getTotalPages();;
            int nowpage = list.getPageable().getPageNumber();//현재페이지//
            int pageBlockSize = 5; // 한 화면에 보일 페이지 수
            int nowPageBlock = nowpage / pageBlockSize;

            model.addAttribute("nowpage",nowpage);
            model.addAttribute("totalPage",totalPage);
            model.addAttribute("pageBlockSize", pageBlockSize);
            model.addAttribute("nowPageBlock", nowPageBlock);
            model.addAttribute("memberList",list.getContent());
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage","오류발생");
        }
        return "/memberManage/memberlist";
    }//

    @RequestMapping(value = "/memberdelete", method = RequestMethod.POST)
    public String memberDelete(@RequestParam("id") String id, RedirectAttributes redirectAttributes) {
        try {
            int result = memberManageService.delete(id);
            if (result > 0) {
                redirectAttributes.addFlashAttribute("successMessage", "회원정보가 삭제되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "정보삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "정보삭제처리 중 오류 발생");
        }
        return "redirect:/members";
    }

    @RequestMapping(value = "/membersearch", method = RequestMethod.GET)
    @ResponseBody
    public List<MemberEntity> memberSearch(
            @RequestParam("searchKey") String searchKey,
            @RequestParam("searchValue") String searchValue) {

        List<MemberEntity> list;

        if(searchKey.equals("id")){
            list=memberManageService.searchById(searchValue);
        }else {
            list=memberManageService.searchByName(searchValue);
        }

        return list;
    }

    // ID null값 감지 추가 해봄
    @RequestMapping(value = "/memberedit", method = RequestMethod.GET)
    public String memberEdit(@RequestParam("id") String id, Model model) {
        System.out.println("수정 요청 ID: " + id); // 로그 추가
        try {
            MemberEntity memberEntity = memberManageService.selectOne(id);
            model.addAttribute("dto", memberEntity);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "회원 정보 조회 중 오류 발생");
        }
        return "/memberManage/memberedit";
    }

    //자체적으로 edit 에서 회원정보수정
    @RequestMapping(value = "/memberupdate", method = RequestMethod.POST)
    public String memberUpdate(MemberDTO dto, RedirectAttributes rd) {
        try {
            int result = memberManageService.update(dto.toEntity());
            if (result > 0) {
                rd.addFlashAttribute("successMessage", "회원 정보가 수정되었습니다.");
            } else {
                rd.addFlashAttribute("errorMessage", "회원 정보 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rd.addFlashAttribute("errorMessage", "회원 정보 수정 중 오류 발생");
        }
        return "redirect:/members";
    }
}
