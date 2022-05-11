package com.coderslab.crm.repository;


import com.coderslab.crm.model.Department;
import com.coderslab.crm.model.Privilege;
import com.coderslab.crm.model.Role;
import com.coderslab.crm.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private PrivilegeRepository privilegeRepository;
    private RoleRepository roleRepository;
    private Validator validator;

    @Test
    public void test(){
        assertEquals(4, 2*2);
    }

    @Test
    public void giveUserDataAndCreate_shouldAddToDatabaseNewUser(){

        User user = new User();

        user.setFirstName("Adam");
        user.setLastName("Kowalski");
        user.setNickname("AKO");
        user.setEmail("adam.kowalski@gmail.com");
        user.setPassword("H4slohaslo!");
        user.setMobilePhoneNumber("123456789");
        user.setInternalPhoneNumber("511");

        String departmentName = "COR";
        String departmentDescription = "Coordination";

        Boolean privilegeSales = true;
        Boolean privilegeService = true;
        Boolean privilegeAccountancy = true;

        Department department = new Department();
        Privilege privilege = new Privilege();

        privilege.setSales(privilegeSales);
        privilege.setService(privilegeService);
        privilege.setAccountancy(privilegeAccountancy);

        department.setName(departmentName);
        department.setDescription(departmentDescription);
        department.setPrivilege(privilege);

        Role role = new Role();
        role.setName("USER");

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setDepartment(department);
        user.setPosition("Planning specialist");
        user.setRoles(roles);
        user.setEnabled(true);

        long beforeAddedUserCount = userRepository.count();

        userRepository.save(user);

        long afterAddedUserCount = userRepository.count();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

//        Assertions.assertNotEquals(afterAddedUserCount,beforeAddedUserCount);
        assertTrue(violations.isEmpty());
    }
}
