package com.coderslab.crm.service;


import com.coderslab.crm.model.Role;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getAdminRole(){
        return roleRepository.getById(2L);
    }

    public void assignUserRole(User user){
        user.setRoles(new HashSet<Role>(Arrays.asList(roleRepository.findById(1L).get())));
    }

    public void assignAdminRole(User user){
        user.getRoles().add(roleRepository.findById(2L).get());
    }

    public void removeAdminRole(User user){
        user.getRoles().remove(roleRepository.findById(2L).get());
    }
}
