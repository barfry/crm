package com.coderslab.crm.controller;

import com.coderslab.crm.model.User;
import com.coderslab.crm.service.AdminService;
import com.coderslab.crm.service.DepartmentService;
import com.coderslab.crm.service.RoleService;
import com.coderslab.crm.validation.OnPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String addUser(@Validated({ OnPersist.class }) User user, BindingResult result, Model model) {
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
            model.addAttribute("users",adminService.getAllUsers());
            model.addAttribute("admin", roleService.getAdminRole());
            return "admin-zone/admin-all-users";
        }
        adminService.revokeAdminRole(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/disable-user")
    public String disableUser(@RequestParam Long id){
        adminService.disableUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/enable-user")
    public String enableUser(@RequestParam Long id){
        adminService.enableUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/search-users-by-firstname")
    public String searchUsersByFirstName(@RequestParam String firstName, Model model){
        model.addAttribute("users", adminService.getUsersByFirstName(firstName));
        model.addAttribute("admin", roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/search-users-by-lastname")
    public String searchUsersByLastName(@RequestParam String lastName, Model model){
        model.addAttribute("users",adminService.getUsersByLastName(lastName));
        model.addAttribute("admin",roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/search-users-by-nickname")
    public String searchUsersByNickname(@RequestParam String nickname, Model model){
        model.addAttribute("users", adminService.getUsersByNickname(nickname));
        model.addAttribute("admin",roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/search-users-by-email")
    public String searchUsersByEmail(@RequestParam String email, Model model){
        model.addAttribute("users", adminService.getUsersByEmail(email));
        model.addAttribute("admin",roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/search-users-by-mobile")
    public String searchUsersByMobilePhoneNumber(@RequestParam String mobilePhoneNumber, Model model){
        model.addAttribute("users", adminService.getUsersByMobile(mobilePhoneNumber));
        model.addAttribute("admin",roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/search-users-by-internal")
    public String searchUsersByInternalPhoneNumber(@RequestParam String internalPhoneNumber, Model model){
        model.addAttribute("users", adminService.getUsersByInternal(internalPhoneNumber));
        model.addAttribute("admin",roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/search-users-by-position")
    public String searchUsersByPosition(@RequestParam String position, Model model){
        model.addAttribute("users", adminService.getUsersByPosition(position));
        model.addAttribute("admin",roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/search-users-by-department")
    public String searchUsersByDepartment(@RequestParam String department, Model model){
        model.addAttribute("users", adminService.getUsersByDepartment(department));
        model.addAttribute("admin",roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/search-users-by-role")
    public String searchUsersByRole(@RequestParam String role, Model model){
        model.addAttribute("users", adminService.getUsersByRole(role));
        model.addAttribute("admin",roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/search-users-by-enabled")
    public String searchUsersByEnabled(@RequestParam Boolean enabled, Model model){
        model.addAttribute("users", adminService.getUsersByEnabled(enabled));
        model.addAttribute("admin",roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

    @GetMapping("/multi-search")
    public String searchUsersByAll(@RequestParam String firstName, @RequestParam String lastname, @RequestParam String nickname, @RequestParam String email, @RequestParam String mobilePhoneNumber, @RequestParam String internalPhoneNumber, @RequestParam String department, @RequestParam String position, @RequestParam String role, @RequestParam Boolean enabled, Model model){
        model.addAttribute("users",adminService.getUsersByMultiSearch(firstName,lastname,nickname,email,mobilePhoneNumber,internalPhoneNumber,department,position,role,enabled));
        model.addAttribute("admin",roleService.getAdminRole());
        return "admin-zone/admin-all-users";
    }

}
