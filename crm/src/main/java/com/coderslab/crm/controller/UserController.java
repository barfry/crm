package com.coderslab.crm.controller;

import com.coderslab.crm.model.Intervention;
import com.coderslab.crm.model.Role;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.RoleRepository;
import com.coderslab.crm.repository.UserRepository;
import com.coderslab.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    RoleRepository roleRepository;
    UserRepository userRepository;

    public UserController(UserService userService, RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/addUser")
    public void addUserUrl(){
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setNickname("ADM");
        user.setEmail("admin@o2.pl");
        user.setPassword("Bl4bl4bl4!");
        user.setMobilePhoneNumber("123456789");
        user.setInternalPhoneNumber("123");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        roleRepository.save(new Role(1L,"USER"));
        roleRepository.save(new Role(2L, "ADMIN"));

        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);
        user.getRoles().add(roleRepository.getById(1L));
        user.getRoles().add(roleRepository.getById(2L));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @GetMapping("/technicians")
    public List<User> getAllTechnicians(){
        return userService.getAllTechnicians();
    }
}
