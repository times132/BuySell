package com.example.giveandtake.controller;

import com.example.giveandtake.DTO.UsersSaveRequestDto;
import com.example.giveandtake.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    // 주입방식은 @Autowired보다 생성자로 주입받는 방식을 권장
    UserRepository userRepository;

    @PostMapping
    public void saveUsers(@RequestBody UsersSaveRequestDto dto){
        userRepository.save(dto.toEntity());
    }



}
