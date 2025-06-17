package com.example.chew.ReviewManage;

import com.example.chew.entity.ReviewEntity;
import com.example.chew.Review.ReviewId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewManageController {

    @Autowired
    ReviewManageService reviewManageService;

    @GetMapping("/reviewlistaa")
    public String reviewList(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageBlockSize = 5;
        Page<ReviewEntity> reviewPage = reviewManageService.reviewlistin(page);
        System.out.println("==== 리뷰 수: " + reviewPage.getContent().size());
        reviewPage.getContent().forEach(r -> {
            System.out.println(">> " + r.getTitle() + " / " + r.getStore().getStorename());
        });
        int nowPage = reviewPage.getPageable().getPageNumber();
        int nowPageBlock = nowPage / pageBlockSize;

        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("nowpage", nowPage );
        model.addAttribute("totalPage", reviewPage.getTotalPages());
        model.addAttribute("nowPageBlock", nowPageBlock);
        model.addAttribute("pageBlockSize", pageBlockSize);

        return "/reviewmanage/reviewlistaa";
    }

    @GetMapping("/review/delete")
    public String deleteReview(@RequestParam("id") String id, @RequestParam("code") int storecode,
                               @RequestParam(defaultValue = "0") int page) {
        reviewManageService.deleteReviewById(new ReviewId(id,storecode));
        return "redirect:/reviewlistaa?page=" + page;
    }
}
