package com.coderslab.crm.repository;

import com.coderslab.crm.model.Department;
import com.coderslab.crm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.lang.Nullable;
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
    @Query("update User u set u.firstName = ?1, u.lastName = ?2, u.nickname = ?3, u.email = ?4, u.mobilePhoneNumber = ?5, u.internalPhoneNumber = ?6, u.department = ?7, u.position = ?8 " +
            "where u.id = ?9")
    void updateUser(String firstName, String lastName, String nickname, String email, String mobilePhoneNumber, String internalPhoneNumber, Department department, String position, Long id);

    long countByRoles_Name(String name);



}
