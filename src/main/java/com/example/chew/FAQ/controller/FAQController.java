package com.example.chew.FAQ.controller;

import com.example.chew.FAQ.service.FAQService;
import com.example.chew.dto.FAQDTO;
import com.example.chew.entity.FAQEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FAQController {
    @Autowired
    FAQService faqService;

    @RequestMapping("/FAQ")
    public String qout(HttpServletRequest request, Model model,
                       @RequestParam(value = "check", required = false, defaultValue = "false") boolean check,
                       @RequestParam(value = "page", required = false, defaultValue = "0") int page) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) ? auth.getName() : null;

        Page<FAQEntity> list;

        if(check) { //내 FAQ만 보기 체크시
            list=faqService.myList(page,id);
        }else { //모든 FAQ 보기
            list=faqService.allList(page);
        }

        int totalPage = list.getTotalPages();;
        int nowpage = list.getPageable().getPageNumber();//현재페이지//
        int pageBlockSize = 5; // 한 화면에 보일 페이지 수
        int nowPageBlock = nowpage / pageBlockSize;

        model.addAttribute("nowpage",nowpage);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageBlockSize", pageBlockSize);
        model.addAttribute("nowPageBlock", nowPageBlock);
        model.addAttribute("qlist",list.getContent());
        model.addAttribute("check", check);
        model.addAttribute("id", id);

        return "/FAQ/FAQout";
    }

    @RequestMapping("/FAQin")
    public String qin() {
        return "/FAQ/FAQin";
    }

    @PostMapping("/FAQsave")
    public String qSave(HttpServletRequest request, FAQDTO faqdto) {
        String title=request.getParameter("title");
        String content=request.getParameter("content");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) ? auth.getName() : null;

        String secret_check=request.getParameter("secret_check");
        //check시 on, 해제시 null
        int secret;
        if(secret_check!=null) secret=1;
        else secret=0;

        faqdto.setId(id);
        faqdto.setSecret(secret);
        FAQEntity entity=faqdto.q_entity();
        entity.setGroups(entity.getNum());
        faqService.insertQuestion(entity);

        return "redirect:/FAQ";
    }

    @ResponseBody
    @PostMapping(value = "/myFAQ", produces = "application/json; charset=UTF-8")
    public Map<String, Object> my(@RequestBody Map<String, Object> payload,
                     HttpServletRequest request) {

        boolean check = (Boolean) payload.get("check");
        int page = (Integer) payload.get("page");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) ? auth.getName() : null;

        Page<FAQEntity> list;

        if (check) {
            list = faqService.myList(page, id);
        } else {
            list = faqService.allList(page);
        }

        int nowpage = list.getPageable().getPageNumber();
        int pageBlockSize = 5;
        int nowPageBlock = nowpage / pageBlockSize;

        Map<String, Object> response = new HashMap<>();
        response.put("qlist", list.getContent());
        response.put("nowpage", list.getPageable().getPageNumber());
        response.put("totalPage", list.getTotalPages());
        response.put("pageBlockSize", pageBlockSize);
        response.put("nowPageBlock", nowPageBlock);
        return response;
    }

    @ResponseBody
    @PostMapping(value="/faq_titleBtn_click", produces = "text/html; charset=UTF-8")
    public String faq_titleBtn_click(String qnum) {
        FAQEntity qdto=faqService.selectQuestion(qnum); //qnum에 해당하는 질문내용 가져옴
        FAQEntity adto=faqService.selectAnswer(qnum); //qnum에 해당하는 답변 가져옴

        // 로그인 사용자 ID 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = (auth != null && auth.isAuthenticated()) ? auth.getName() : "guest";

        StringBuilder sb = new StringBuilder();

        sb.append("<div class='question' style='padding-left:140px; text-align: left;'>");
        sb.append("<pre style='text-align: left;'>"+qdto.getContent()+"</pre>");
        if(id.equals("admin") && (adto == null || adto.getContent() == null || adto.getContent().isEmpty()) && !qdto.getState().equals("답변"))
            sb.append("<div style='text-align: left;'><input type='button' value='답변쓰기' class='reply_inputBtn' style='margin-top: 20px; margin-left: 0px;' data-qnum='" + qdto.getNum() + "'></div>");
        else if(id.equals("admin") && !qdto.getState().equals("답변"))
            sb.append("<div style='text-align: left;'><input type='button' value='답변수정' class='reply_updateBtn' style='margin-top: 20px; margin-left: 0px;' data-qnum='" + qdto.getNum() + "'></div>");
        sb.append("</div>");

        sb.append("<div class='answer' style='padding-left: 140px; margin-top: 20px; text-align: left;'>");
        if (adto != null && adto.getContent() != null) {
            sb.append("<strong>답변:</strong> ").append("<pre style='text-align: left;'>"+adto.getContent()+"</pre>");
        }
        sb.append("</div>");

        return sb.toString();
    }

    @ResponseBody
    @RequestMapping(value="/faq_reply_input", produces = "text/html; charset=UTF-8")
    public String faq_reply(String qnum) {
        StringBuilder sb = new StringBuilder();

        sb.append("<form id='replyForm' style='padding-left:140px;'>");
        sb.append("<textarea name='content' rows='4' cols='60' placeholder='답변을 입력하세요'></textarea><br>");
        sb.append("<input type='hidden' name='qnum' value='" + qnum + "'>");
        sb.append("<input type='submit' value='등록'>");
        sb.append("</form>");

        return sb.toString();
    }

    @ResponseBody
    @RequestMapping(value="/faq_save_reply", method= RequestMethod.POST)
    public String faq_save_reply(String qnum, String content, HttpSession session) {
        // 로그인한 사용자 정보가 세션에 있다고 가정
        String id = (String) session.getAttribute("id");

        // DTO에 담기
        FAQDTO dto = new FAQDTO();
        dto.setNum(Integer.parseInt(qnum));
        dto.setGroups(dto.getNum());
        dto.setTitle("관리자 답변");
        dto.setContent(content);
        dto.setId(id);

        try {
            faqService.insertAnswer(dto.a_entity());
            faqService.updateQuestionState(dto.getNum());
            return "success";
        } catch(Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @ResponseBody
    @RequestMapping(value="/faq_reply_updateForm", produces = "text/html; charset=UTF-8")
    public String faq_reply_updateForm(String qnum) {
        StringBuilder sb = new StringBuilder();

        sb.append("<form id='replyUpdateForm' style='padding-left:140px;'>");
        sb.append("<textarea name='content' rows='4' cols='60' placeholder='답변을 입력하세요'></textarea><br>");
        sb.append("<input type='hidden' name='qnum' value='" + qnum + "'>");
        sb.append("<input type='submit' value='등록'>");
        sb.append("</form>");

        return sb.toString();
    }

    @ResponseBody
    @RequestMapping(value="/faq_reply_update", method=RequestMethod.POST)
    public String faq_reply_update(String qnum, String content, HttpSession session) {
        // 로그인한 사용자 정보
        String id = (String) session.getAttribute("id");

        // DTO에 담기
        FAQDTO dto = new FAQDTO();
        dto.setGroups(Integer.parseInt(qnum));
        dto.setContent(content);
        dto.setId(id);
        System.out.println("qnum = " + qnum + ", content = " + content);
        try {
            faqService.updateAnswer(dto.a_entity());
            return "success";
        } catch(Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @ResponseBody
    @PostMapping(value="/faq_secretBtn_click", produces = "text/html; charset=UTF-8")
    public String faq_secretBtn_click(String qnum) {
        FAQEntity qdto=faqService.selectQuestion(qnum); //qnum에 해당하는 질문내용 가져옴

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = (auth != null && auth.isAuthenticated()) ? auth.getName() : "guest";

        if(id.equals(qdto.getId()) || id.equals("admin")) {
            FAQEntity adto=faqService.selectAnswer(qnum); //qnum에 해당하는 답변 가져옴

            StringBuilder sb = new StringBuilder();

            sb.append("<div class='question' style='padding-left:140px; text-align: left;'>");
            sb.append("<pre style='text-align: left;'>"+qdto.getContent()+"</pre>");
            if(id.equals("admin") && (adto == null || adto.getContent() == null || adto.getContent().isEmpty()))
                sb.append("<div style='text-align: left;'><input type='button' value='답변쓰기' class='reply_inputBtn' style='margin-top: 20px; margin-left: 0;' data-qnum='" + qdto.getNum() + "'></div>");
            else if(id.equals("admin"))
                sb.append("<div style='text-align: left;'><input type='button' value='답변수정' class='reply_updateBtn' style='margin-top: 20px; margin-left: 0;' data-qnum='" + qdto.getNum() + "'></div>");
            sb.append("</div>");

            sb.append("<div class='answer' style='padding-left: 140px; margin-top: 20px; text-align: left;'>");
            if (adto != null && adto.getContent() != null) {
                sb.append("<strong>답변:</strong> ").append("<pre style='text-align: left;'>"+adto.getContent()+"</pre>");
            }
            sb.append("</div>");

            return sb.toString();
        }else {
            return "fail";
        }

    }

    @GetMapping(value = "/deleteItem")
    public String deleteItem(@RequestParam("num") int num){
        faqService.deleteByNum(num);
        return "redirect:/FAQ";
    }
}
