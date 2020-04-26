package com.buysell.controller;

import com.buysell.common.Pagination;
import com.buysell.common.SearchCriteria;
import com.buysell.domain.entity.Role;
import com.buysell.domain.entity.User;
import com.buysell.domain.entity.UserRoles;
import com.buysell.service.AdminService;
import com.buysell.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    private UserService userService;

    // 어드민 페이지
    @GetMapping
    public String adminGET() {
        return "/admin/admin";
    }

    //회원정보보기 및 삭제
    @GetMapping("/userlist")
    public String userListGET(SearchCriteria searchCri, Model model) {
        searchCri.setPageSize(10); // 한 화면에 유저 10개씩 표시
        Page<User> userPage = adminService.getList(searchCri);

        model.addAttribute("userList", userPage.getContent());
        model.addAttribute("pageMaker", Pagination.builder()
                .cri(searchCri)
                .total(userPage.getTotalElements())
                .realEndPage(userPage.getTotalPages())
                .listSize(5) // 페이징 5로 설정
                .build());
        return "/admin/userList";
    }
    //회원 롤 관리
    @GetMapping("/role")
    public String userRoleListGET(SearchCriteria searchCri, Model model) {
        searchCri.setPageSize(10); // 한 화면에 유저 10개씩 표시
        Page<UserRoles> userPage = adminService.getRole(searchCri);
        List<Role> roles= adminService.findAllRole();
        model.addAttribute("roles", roles );
        model.addAttribute("userRole", userPage.getContent());
        model.addAttribute("pageMaker", Pagination.builder()
                .cri(searchCri)
                .total(userPage.getTotalElements())
                .realEndPage(userPage.getTotalPages())
                .listSize(5) // 페이징 5로 설정
                .build());
        return "/admin/role";
    }

    //회원 삭제
    @GetMapping("/userlist/delete")
    public String deleteGET(@RequestParam("id") Long id, @ModelAttribute("cri") SearchCriteria cri) {
        userService.delete(id);
        return "redirect:/admin/userlist";
    }

    //롤 삭제
    @GetMapping("/userrole/delete")
    public String deleteRoleGET(@RequestParam("id") Long id, @ModelAttribute("cri") SearchCriteria cri) {
        adminService.deleteUserRole(id);
        return "redirect:/admin/role";
    }
    //롤 추가
    @GetMapping("/userrole/add")
    public String addUserRoleGET(@RequestParam("username") String username, @RequestParam("roleName") String roleName, @ModelAttribute("cri") SearchCriteria cri) {
        System.out.println("**delete**" + username);
        adminService.addUserRole(username, roleName);
        return "redirect:/admin/role";
    }



}
