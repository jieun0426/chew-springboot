package com.example.chew.Detail;


import com.example.chew.Image.ImageDTO;
import com.example.chew.Image.ImageService;
import com.example.chew.entity.ReviewEntity;
import com.example.chew.Review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DetailController {

    @Autowired
    DetailService detailService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    ImageService imageService;


    @GetMapping("/detailmain")
    public String showDetailMain(@RequestParam(name = "nowPage", defaultValue = "1") int page, Model model) {
        int cntPerPage = 6;
        int total = detailService.countAllRecords();
        PageDTO pageDTO = new PageDTO(total, page, cntPerPage);

        List<DetailDTO> storeList = detailService.getPagedStores(pageDTO.getStart(), pageDTO.getEnd());
        model.addAttribute("list", storeList);
        model.addAttribute("pdto", pageDTO);

        return "detail/detailmain";
    }

    // POST 요청 처리
    @PostMapping("/detailmain_search")
    public String searchDetailMainPost(@RequestParam("search") String search,
                                       @RequestParam(name = "nowPage", defaultValue = "1") int page,
                                       Model model) {
        return processSearch(search, page, model);
    }

    // GET 요청 처리
    @GetMapping("/detailmain_search")
    public String searchDetailMainGet(@RequestParam("search") String search,
                                      @RequestParam(name = "nowPage", defaultValue = "1") int page,
                                      Model model) {
        return processSearch(search, page, model);
    }

    // 중복 코드 제거를 위한 공통 처리 메서드
    private String processSearch(String search, int page, Model model) {
        int cntPerPage = 6;
        int total = detailService.countSearchRecords(search);
        PageDTO pageDTO = new PageDTO(total, page, cntPerPage);

        List<DetailDTO> searchResult = detailService.searchStores(search, pageDTO.getStart(), pageDTO.getEnd());

        model.addAttribute("list", searchResult);
        model.addAttribute("pdto", pageDTO);
        model.addAttribute("search", search);
        model.addAttribute("count", total);

        return "detail/detailmain";
    }


    @GetMapping("/detailview")
    public String detailView(@RequestParam("storecode") int storecode, Model model) {
        DetailDTO ddto = detailService.getStoreDetail(storecode);
        model.addAttribute("ddto", ddto);

        double avgStars = reviewService.getAverageStars(storecode);
        int filledStars = (int) Math.min(Math.round(avgStars), 5);
        int emptyStars = 5 - filledStars;
        model.addAttribute("filledStars", filledStars);
        model.addAttribute("emptyStars", emptyStars);
        model.addAttribute("avgStars", avgStars);

        List<ImageDTO> imagelist = imageService.getImagesByStoreCode(storecode);
        model.addAttribute("imagelist", imagelist);

        List<ReviewEntity> reviews = reviewService.getReviewsByStorecode(storecode);
        model.addAttribute("reviews", reviews);
        System.out.println("불러온 리뷰 개수(detailview): " + reviews.size());

        return "detail/detailview";
    }




}
