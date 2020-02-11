package com.example.giveandtake.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@AllArgsConstructor


public class Homecontroller {
    @GetMapping("/")// 메인 페이지
    public String home() {
        return "home";
    }
}
