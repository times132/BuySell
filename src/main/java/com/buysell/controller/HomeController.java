package com.buysell.controller;
import com.buysell.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping("/")// 메인 페이지
    public String home() {
        return "/home";
    }
}
