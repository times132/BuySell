package com.example.giveandtake.controller;

import com.example.giveandtake.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    private CategoryService categoryService;

    @GetMapping("/")// 메인 페이지
    public String home() {
        return "/home";
    }
}
