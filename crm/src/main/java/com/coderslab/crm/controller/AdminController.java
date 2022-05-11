package com.coderslab.crm.controller;

import com.coderslab.crm.model.User;
import com.coderslab.crm.service.AdminService;
import com.coderslab.crm.service.DepartmentService;
import com.coderslab.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    DepartmentService departmentService;
    AdminService adminService;
    RoleService roleService;


    public AdminController(DepartmentService departmentService, AdminService adminService, RoleService roleService) {
        this.departmentService = departmentService;
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String showAdminIndex(){
        return "admin-zone/admin-index";
    }

    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users",adminService.getAllUsers());
        model.addAttribute("admin", roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/sign-new-user")
    public String showSignUpForm(User user, Model model) {
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        return "admin-zone/admin-sign-user";
    }

    @PostMapping("/sign-new-user")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("departmentList", departmentService.getAllDepartments());
            return "admin-zone/admin-sign-user";
        }
        adminService.addNewUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/remove-user")
    public String removeUser(@RequestParam Long id) {
        adminService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit-user")
    public String initEditUser(@RequestParam Long id, Model model){
        model.addAttribute("user", adminService.getUserById(id));
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        return "admin-zone/admin-edit-user";
    }

    @PostMapping("/edit-user")
    public String editUser(@ModelAttribute @Valid User user, BindingResult result, Model model) {
        user.setPassword(adminService.getUserById(user.getId()).getPassword());
        if (result.hasErrors()){
            model.addAttribute("departmentList", departmentService.getAllDepartments());
            return "admin-zone/admin-edit-user";
        }
        adminService.editUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/grant-admin-role")
    public String grantAdminRole(@RequestParam Long id) {
        adminService.addAdminRole(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/revoke-admin-role")
    public String revokeAdminRole(@RequestParam Long id, Model model){
        if(adminService.countAdmins() == 1){
            model.addAttribute("error","error");
            return "";
        }
        adminService.revokeAdminRole(id);
        return "redirect:/admin/users";
    }

}
