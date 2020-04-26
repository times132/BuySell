package com.buysell.service;

import com.buysell.domain.DTO.UserRolesDTO;
import com.buysell.repository.RoleRepository;
import com.buysell.repository.UserRepository;
import com.buysell.repository.UserRolesRepository;
import com.buysell.exception.AppException;
import com.buysell.common.SearchCriteria;
import com.buysell.domain.RoleName;
import com.buysell.mapper.UserMapper;
import com.buysell.domain.entity.Role;
import com.buysell.domain.entity.User;
import com.buysell.domain.entity.UserRoles;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<Role> roles = roleRepository.findAll();

        return roles;
    }

    public void deleteUserRole(Long id) {
        userRolesRepository.deleteById(id);
    }

    public void addUserRole(String username, String roleName) {
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
