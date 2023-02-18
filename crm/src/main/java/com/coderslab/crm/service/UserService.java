package com.coderslab.crm.service;

import com.coderslab.crm.model.Role;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.RoleRepository;
import com.coderslab.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public UserService() {
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user != null){
            return user;
        }
        throw new UsernameNotFoundException("E-mail not found: "+email);
    }

    public void addNewUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setEnabled(true);
        roleService.assignUserRole(user);
        userRepository.save(user);
    }

    public List<User> usersWithServicePrivilege(){
        return userRepository.getUsersByDepartmentServicePrivilege();
    }

    public List<User> getActiveUsers(){
        return userRepository.getAllByEnabledTrue();
    }

    public void deleteUserById(Long id){
        userRepository.delete(userRepository.getById(id));
    }

    public User getUserById(Long id){
        return userRepository.getById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<User> getUsersByDepartment(String department){
        return userRepository.getUsersByDepartment(department);
    }

    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<User> getAllTechnicians() {
        List<User> technicians = userRepository.getUsersByDepartmentName("SERVICE/HFO");
        technicians.forEach(t -> t.setTitle(t.getFirstName() +  ' ' + t.getLastName()));
        return technicians;
    }
}
