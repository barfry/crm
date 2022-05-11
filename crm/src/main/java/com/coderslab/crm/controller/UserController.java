package com.coderslab.crm.controller;

import com.coderslab.crm.model.Role;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.RoleRepository;
import com.coderslab.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

        user.setEnabled(true);
        userRepository.save(user);
    }
}
