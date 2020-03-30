package com.example.giveandtake.service;

import com.example.giveandtake.common.SearchCriteria;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.model.entity.UserRoles;
import com.example.giveandtake.repository.UserRepository;
import com.example.giveandtake.repository.UserRolesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private UserRepository userRepository;
    private UserRolesRepository userRolesRepository;

    // 게시물 목록, 페이징, 검색
    public Page<User> getList(SearchCriteria SearchCri){
        Pageable pageable = PageRequest.of(SearchCri.getPage()-1, SearchCri.getPageSize(), Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<User> page;


        if (SearchCri.getType().equals("")){
            page = userRepository.findAll(pageable);
        }
        else if (SearchCri.getType().equals("E")){
            page = userRepository.findAllByEmailContaining(SearchCri.getKeyword(), pageable);
        }
        else{
            page = userRepository.findAllByNicknameContaining(SearchCri.getKeyword(), pageable);
        }
        return page;
    }


    public List<UserRoles> getRole(String type){
        List<UserRoles> userRoles = userRolesRepository.findAll();
        return userRoles;
    }
}
