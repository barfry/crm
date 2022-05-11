package com.coderslab.crm.service;

import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;
    RoleService roleService;

    public AdminService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public void addNewUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setEnabled(true);
        roleService.assignUserRole(user);
        userRepository.save(user);
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

    public void editUser(User user){
        userRepository.updateUser(user.getFirstName(),user.getLastName(),user.getNickname(),user.getEmail(),user.getMobilePhoneNumber(),user.getInternalPhoneNumber(),user.getDepartment(),user.getPosition(), user.getId());
    }

    public void addAdminRole(Long id){
        User user = getUserById(id);
        roleService.assignAdminRole(getUserById(id));
        userRepository.save(user);
    }

    public List<User> getUsersByDepartment(String department){
        return userRepository.getUsersByDepartment(department);
    }

    public void revokeAdminRole(Long id){
        User user = getUserById(id);
        roleService.removeAdminRole(user);
        userRepository.save(user);
    }

    public long countAdmins(){
        return userRepository.countByRoles_Name("ADMIN");
    }
}
