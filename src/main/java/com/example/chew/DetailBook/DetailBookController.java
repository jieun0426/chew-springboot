package com.example.chew.DetailBook;

import com.example.chew.entity.BookingEntity;
import com.example.chew.entity.StoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DetailBookController {

    @Autowired
    DetailBookService detailBookService;

    @GetMapping("/logincheck")
    @ResponseBody
    public String loginCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return "fail";
        }
        return "ok";
    }

    @PostMapping("/bookingsave")
    @ResponseBody
    public String saveBooking(@ModelAttribute BookingEntity bookingEntity, @RequestParam("storecode") int storecode) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return "login_required";
        }

        try {
            // 로그인된 사용자 ID 설정
            String username = auth.getName(); // 또는 ((UserDetails)auth.getPrincipal()).getUsername();
            bookingEntity.setId(username);
            bookingEntity.setStore(StoreEntity.builder().storecode(storecode).build());
            bookingEntity.setState("대기");

            // 예약 번호 (PK)가 자동 생성되도록 시퀀스 또는 GenerationType.AUTO가 필요
            detailBookService.saveBooking(bookingEntity);

            return "success";
        } catch (Exception e) {
            return "error:" + e.getMessage();
        }
    }

}
