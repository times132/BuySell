package com.example.giveandtake.controller;

import com.example.giveandtake.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class Homecontroller {

    private CategoryService categoryService;

    @GetMapping("/")// 메인 페이지
    public String home(Model model) {
        model.addAttribute("category", categoryService.getCategory());

        return "/home";
    }
}
