package com.coderslab.crm.controller;

import com.coderslab.crm.filter.UserFilter;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.UserRepository;
import com.coderslab.crm.service.AdminService;
import com.coderslab.crm.service.DepartmentService;
import com.coderslab.crm.service.RoleService;
import com.coderslab.crm.validation.OnPersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes("userFilter")
public class AdminController {

    @Autowired
    DepartmentService departmentService;
    AdminService adminService;
    RoleService roleService;
    UserRepository userRepository;

    public AdminController(DepartmentService departmentService, AdminService adminService, RoleService roleService, UserRepository userRepository) {
        this.departmentService = departmentService;
        this.adminService = adminService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showAdminIndex(){
        return "admin-zone/admin-index";
    }

    @GetMapping("/users")
    public String showUsers(Model model){
        UserFilter userFilter = new UserFilter();
        model.addAttribute("userFilter", userFilter);
        return showUsersPage(userFilter,1,"id","asc", model);
    }

    @GetMapping("/users/page/{pageNo}")
    public String showUsersPage(@ModelAttribute UserFilter userFilter, @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "sortField", defaultValue = "id") String sortField, @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page < User > page = adminService.findUsersBySearchWithPaginationAndSorting(pageNo, pageSize, sortField, sortDir, userFilter);
        List < User > listUsers = page.getContent();

        model.addAttribute("userFilter", userFilter);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("users",listUsers);
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
    public String initEditUser(@RequestParam(value = "id") Long id, Model model){
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

}
