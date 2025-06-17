package com.example.chew.Review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/review/submit")
    public String submitReview(@RequestParam("storecode") int storecode,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam("stars") double stars) {

        reviewService.saveReview(storecode,title,content,stars);
        return "redirect:/detailview?storecode=" + storecode;
    }

    @PostMapping("/review/deleteReview")
    public String deleteReview(@RequestParam String id,
                               @RequestParam int storecode,
                               Principal principal) {
        if (!principal.getName().equals(id)) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
        reviewService.deleteReview(id, storecode);

        return "redirect:/detailview?storecode=" + storecode;
    }

    @PostMapping("/review/update")
    public String reviewUpdate(@RequestParam String id,
                               @RequestParam int storecode,
                               @RequestParam String title,
                               @RequestParam String content,
                               @RequestParam int stars,
                               Principal principal) {
        if (!principal.getName().equals(id)) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
        reviewService.updateReview(id, storecode, title, content, stars);

        return "redirect:/detailview?storecode=" + storecode;
    }




}
