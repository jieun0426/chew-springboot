package com.example.chew.main;


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
public class MainController {

    @Autowired
    com.example.chew.main.MainService mainService;


    @GetMapping({"/","/main"})
    public String mainPage(Model model) {
        List<MainInterface> list = mainService.getTop3StoresByreview();
        model.addAttribute("toplist", list);
        return "main";
    }

    @PostMapping("/mainSelectType")
    public String selectTopStoresByArea(@RequestParam("area") String area, Model model) {
        List<MainInterface>list = mainService.getTop3StoresByArea(area);
        model.addAttribute("list", list);
        return "fragments/areaList :: areaListContent";
    }








}
