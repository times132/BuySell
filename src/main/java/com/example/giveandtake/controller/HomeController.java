package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.UsersSaveRequestDto;
import com.example.giveandtake.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class HomeController {

    UserRepository userRepository;

    @GetMapping("/home")
    public String home(){
        return "Home";
    }

    @PostMapping("/users")
    public void saveUsers(@RequestBody UsersSaveRequestDto dto){
        userRepository.save(dto.toEntity());
    }
}
