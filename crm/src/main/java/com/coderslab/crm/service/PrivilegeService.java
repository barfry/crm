package com.coderslab.crm.service;

import com.coderslab.crm.model.Department;
import com.coderslab.crm.model.Privilege;
import com.coderslab.crm.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {

    @Autowired
    PrivilegeRepository privilegeRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public List<Privilege> getAllPrivileges(){
        return privilegeRepository.findAll();
    }
}
