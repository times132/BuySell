package com.example.giveandtake.service;

import com.example.giveandtake.DTO.UserRolesDTO;
import com.example.giveandtake.common.AppException;
import com.example.giveandtake.common.SearchCriteria;
import com.example.giveandtake.domain.RoleName;
import com.example.giveandtake.mapper.UserMapper;
import com.example.giveandtake.model.entity.Role;
import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.model.entity.UserRoles;
import com.example.giveandtake.repository.RoleRepository;
import com.example.giveandtake.repository.UserRepository;
import com.example.giveandtake.repository.UserRolesRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {

    private UserRepository userRepository;
    private UserRolesRepository userRolesRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;

    // 유저 목록, 페이징, 검색
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

//유저롤 목록
    public Page<UserRoles> getRole(SearchCriteria SearchCri) {
        Pageable pageable = PageRequest.of(SearchCri.getPage()-1, SearchCri.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));

        Page<UserRoles> page;
        page = userRolesRepository.findAll(pageable);

        if (SearchCri.getType().equals("")){
            page = userRolesRepository.findAll(pageable);
        }


        return page;
    }

    public List<Role> findAllRole() {
        List<Role> roles = new ArrayList<>();
        roles = roleRepository.findAll();
        return roles;
    }

    public void deleteUserRole(Long id) {
        userRolesRepository.deleteById(id);
    }

    public void addUserRole(String username, String roleName) {
        System.out.println(username + roleName);
        User user = userRepository.findByUsername(username);
        Role role = null;
        if (roleName.equals("ROLE_USER")) {
            role = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set"));
        }
        else if (roleName.equals("ROLE_GUEST")) {
            role = roleRepository.findByName(RoleName.ROLE_GUEST)
                    .orElseThrow(() -> new AppException("User Role not set"));
        }
        else  if (roleName.equals("ROLE_SOCIAL")) {
            role = roleRepository.findByName(RoleName.ROLE_SOCIAL)
                    .orElseThrow(() -> new AppException("User Role not set"));
        }
        else  if (roleName.equals("ROLE_ADMIN")) {
            role = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new AppException("User Role not set"));
        }
        UserRolesDTO dto = new UserRolesDTO();
        dto.setUser(user);
        dto.setRole(role);
        userRolesRepository.save(userMapper.userRolestoEntity(dto));
    }
}
