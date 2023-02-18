package com.coderslab.crm.repository;

import com.coderslab.crm.model.Department;
import com.coderslab.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE u.department=?1")
    List<User> getUsersByDepartment(String department);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.firstName = ?1, u.lastName = ?2, u.nickname = ?3, u.email = ?4, u.mobilePhoneNumber = ?5, u.internalPhoneNumber = ?6, u.department = ?7, u.position = ?8" +
            "where u.id = ?9")
    void updateUser(String firstName, String lastName, String nickname, String email, String mobilePhoneNumber, String internalPhoneNumber, Department department, String position, Long id);

    long countByRoles_Name(String name);

    long countByDepartment_Id(Long id);

    List<User> getUsersByDepartmentName(String departmentName);


    @Query(value = "SELECT u FROM User u JOIN Role r WHERE r.id = ?1")
    List<User> getUsersByRoleId(Long id);

    List<User> getAllByEnabledTrue();

    @Query(value = "SELECT * FROM users JOIN departments d on users.department_id = d.id JOIN privileges p on d.privilege_id = p.id WHERE service=true", nativeQuery = true)
    List<User> getUsersByDepartmentServicePrivilege();

}
