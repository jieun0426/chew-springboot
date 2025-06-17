package com.example.chew.bookmanage.controller;

import com.example.chew.bookmanage.service.BookManageService;
import com.example.chew.dto.BookingDTO;
import com.example.chew.entity.BookingEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Controller
public class BookManageController {
    @Autowired
    BookManageService bookManageService;

    // 예약 목록 (페이징)
    @RequestMapping(value = "/booklist")
    public String bookman1(HttpServletRequest request, HttpServletResponse response, Model model,
                           @RequestParam(value = "page", required = false, defaultValue = "0") int page) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) ? auth.getName() : null;

        // 관리자 로그인 체크
        if (id == null || !id.contains("admin")) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter pww = response.getWriter();
            pww.print("<script>alert('관리자 계정으로 로그인 해주세요.')</script>");
            pww.print("<script>location.href='loginput'</script>");
            pww.close();
            return null;
        }

        Page<BookingEntity> list=bookManageService.allList(page);;

        int totalPage = list.getTotalPages();;
        int nowpage = list.getPageable().getPageNumber();//현재페이지//
        int pageBlockSize = 5; // 한 화면에 보일 페이지 수
        int nowPageBlock = nowpage / pageBlockSize;

        model.addAttribute("nowpage",nowpage);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageBlockSize", pageBlockSize);
        model.addAttribute("nowPageBlock", nowPageBlock);
        model.addAttribute("bookma",list.getContent());

        return "/bookManage/booklist";
    }

    // 예약 상세 - 여러 건
    @RequestMapping(value = "/bookmanage_detail")
    public String bookman2(HttpServletRequest request, Model mo) {
        int tablenum = Integer.parseInt(request.getParameter("tablenum"));

        BookingEntity detail=bookManageService.detailOne(tablenum);
        mo.addAttribute("detail", detail);
        return "/bookManage/bookmanage_detail";
    }

    // 예약 정보 업데이트
    @RequestMapping(value = "/updateBookings", method = RequestMethod.POST)
    public String updateBookings(BookingDTO dto) {
        bookManageService.updateBooking(dto.entity());
        System.out.println("dto:"+dto.toString());
        System.out.println("entity:"+dto.entity().toString());
        return "redirect:/booklist";
    }

    @RequestMapping(value = "/bookmanage_search")
    public String bookman3(Model model, @RequestParam("search") String search,
                           @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String id = (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) ? auth.getName() : null;

        Page<BookingEntity> list=bookManageService.searchList(page, search);

        int totalPage = list.getTotalPages();;
        int nowpage = list.getPageable().getPageNumber();//현재페이지//
        int pageBlockSize = 5; // 한 화면에 보일 페이지 수
        int nowPageBlock = nowpage / pageBlockSize;

        model.addAttribute("nowpage",nowpage);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("pageBlockSize", pageBlockSize);
        model.addAttribute("nowPageBlock", nowPageBlock);
        model.addAttribute("bookma",list.getContent());

        return "/bookManage/bookmanage_search";
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/deleteBookingSelectedItems")
    public String deleteSelectedItems(@RequestParam("ids") String ids) {
        // 콤마로 구분된 문자열을 List<String>으로 변환
        List<String> idList = Arrays.asList(ids.split(","));

        // 각 id에 대해 삭제 작업 수행
        for (String id : idList) {
            try {
                int tablenum = Integer.parseInt(id);
                bookManageService.deleteBookingByTablenum(tablenum);
            }catch (Exception e) {
                System.out.println("예외 발생: " + e.getMessage());
                e.printStackTrace(System.out);  // 확실하게 출력
                return "삭제 중 오류가 발생했습니다. 관리자에게 문의하세요.";
            }

        }
        return "ok";
    }
}
