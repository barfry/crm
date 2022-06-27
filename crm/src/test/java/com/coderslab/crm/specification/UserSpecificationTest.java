package com.coderslab.crm.specification;

import com.coderslab.crm.filter.UserFilter;
import com.coderslab.crm.repository.DepartmentRepository;
import com.coderslab.crm.repository.PrivilegeRepository;
import com.coderslab.crm.repository.RoleRepository;
import com.coderslab.crm.repository.UserRepository;
import com.coderslab.crm.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.Validator;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserSpecificationTest {

    @Autowired
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private PrivilegeRepository privilegeRepository;
    private RoleRepository roleRepository;
    private Validator validator;
    private AdminService adminService;

    public UserSpecificationTest(UserRepository userRepository, DepartmentRepository departmentRepository, PrivilegeRepository privilegeRepository, RoleRepository roleRepository, Validator validator, AdminService adminService) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
        this.validator = validator;
        this.adminService = adminService;
    }

    @Test
    public void giveNoSearchData_ReceiveAllUsers() {

        UserFilter userFilter = new UserFilter();


        adminService.findUsersBySearchWithPaginationAndSorting(1,10, "id", "asc",userFilter);

    }
}
