package com.coderslab.crm.service;

import com.coderslab.crm.filter.UserFilter;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.UserRepository;
import com.coderslab.crm.specification.SearchCriteria;
import com.coderslab.crm.specification.UserSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
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
        if(getUserById(id).getRoles().contains("ADMIN")){

        }

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

    public void revokeAdminRole(Long id){
        User user = getUserById(id);
        roleService.removeAdminRole(user);
        userRepository.save(user);
    }

    public long countAdmins(){
        return userRepository.countByRoles_Name("ADMIN");
    }

    public void disableUser(Long id){
        User user = getUserById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

    public void enableUser(Long id){
        User user = getUserById(id);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public Page<User> findUsersBySearchWithPaginationAndSorting(int pageNo, int pageSize, String sortField, String sortDirection, UserFilter userFilter){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        UserSpecification spec1 = new UserSpecification(new SearchCriteria("firstName",":",userFilter.getFirstName()));
        UserSpecification spec2 = new UserSpecification(new SearchCriteria("lastName",":",userFilter.getLastName()));
        UserSpecification spec3 = new UserSpecification(new SearchCriteria("nickname",":",userFilter.getNickname()));
        UserSpecification spec4 = new UserSpecification(new SearchCriteria("email",":",userFilter.getEmail()));
        UserSpecification spec5 = new UserSpecification(new SearchCriteria("mobilePhoneNumber",":",userFilter.getMobilePhoneNumber()));
        UserSpecification spec6 = new UserSpecification(new SearchCriteria("internalPhoneNumber",":",userFilter.getInternalPhoneNumber()));
        UserSpecification spec7 = new UserSpecification(new SearchCriteria("position",":",userFilter.getPosition()));
        UserSpecification spec8 = new UserSpecification(new SearchCriteria("department",":", userFilter.getDepartmentName()));
        UserSpecification spec9 = new UserSpecification(new SearchCriteria("roles",":", userFilter.getRoleName()));

        return this.userRepository.findAll(Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5).and(spec6).and(spec7).and(spec8).and(spec9), pageable);
    }

    public long countUsersInDepartment(Long id){
        return userRepository.countByDepartment_Id(id);
    }

    public Page<User> findUsersWithUserRoleBySearchWithPaginationAndSorting(int pageNo, int pageSize, String sortField, String sortDirection, UserFilter userFilter){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        UserSpecification spec1 = new UserSpecification(new SearchCriteria("firstName",":",userFilter.getFirstName()));
        UserSpecification spec2 = new UserSpecification(new SearchCriteria("lastName",":",userFilter.getLastName()));
        UserSpecification spec3 = new UserSpecification(new SearchCriteria("nickname",":",userFilter.getNickname()));
        UserSpecification spec4 = new UserSpecification(new SearchCriteria("email",":",userFilter.getEmail()));
        UserSpecification spec5 = new UserSpecification(new SearchCriteria("mobilePhoneNumber",":",userFilter.getMobilePhoneNumber()));
        UserSpecification spec6 = new UserSpecification(new SearchCriteria("internalPhoneNumber",":",userFilter.getInternalPhoneNumber()));
        UserSpecification spec7 = new UserSpecification(new SearchCriteria("position",":",userFilter.getPosition()));
        UserSpecification spec8 = new UserSpecification(new SearchCriteria("department",":", userFilter.getDepartmentName()));
        UserSpecification spec9 = new UserSpecification(new SearchCriteria("roles",":", "user"));

        return this.userRepository.findAll(Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5).and(spec6).and(spec7).and(spec8).and(spec9), pageable);
    }

}

