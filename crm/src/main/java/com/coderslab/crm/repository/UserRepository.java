package com.coderslab.crm.repository;

import com.coderslab.crm.model.Department;
import com.coderslab.crm.model.Role;
import com.coderslab.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.department=?1")
    public List<User> getUsersByDepartment(String department);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.firstName = ?1, u.lastName = ?2, u.nickname = ?3, u.email = ?4, u.mobilePhoneNumber = ?5, u.internalPhoneNumber = ?6, u.department = ?7, u.position = ?8 " +
            "where u.id = ?9")
    void updateUser(String firstName, String lastName, String nickname, String email, String mobilePhoneNumber, String internalPhoneNumber, Department department, String position, Long id);

    long countByRoles_Name(String name);

    List<User> findByFirstNameContains(String firstName);

    List<User> findByLastNameContains(String lastName);

    List<User> findByNicknameContains(String nickname);

    List<User> findByEmailContains(String email);

    List<User> findByMobilePhoneNumberContains(String mobilePhoneNumber);

    List<User> findByInternalPhoneNumberContains(String internalPhoneNumber);

    List<User> findByDepartment_NameContains(String name);

    List<User> findByPositionContains(String position);

    List<User> findByRoles_NameContains(String name);

    List<User> findByEnabledIs(Boolean enabled);

    List<User> findByFirstNameContainsAndLastNameContainsAndNicknameContainsAndEmailContainsAndMobilePhoneNumberContainsAndInternalPhoneNumberContainsAndDepartment_NameContainsAndPositionContainsAndRoles_NameContainsAndEnabledIs(@Nullable String firstName, @Nullable String lastName, @Nullable String nickname, @Nullable String email, @Nullable String mobilePhoneNumber, @Nullable String internalPhoneNumber, @Nullable String name, @Nullable String position, @Nullable String name1, @Nullable Boolean enabled);






























}
