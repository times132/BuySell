package com.example.giveandtake.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController //@Controller + @ResponseBody 의 축약형으로써, 리턴값을 뷰리졸버로 매핑하지 않고 그대로 출력해준다.
public class HelloController {


    @RequestMapping(method = RequestMethod.GET , path = "/hello")   //localhost:8080/api/getMethod
    public String home(){
        return "HOME HELLO";
    }



}
