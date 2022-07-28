package com.coderslab.crm.service;

import com.coderslab.crm.filter.DepartmentFilter;
import com.coderslab.crm.filter.UserFilter;
import com.coderslab.crm.model.Department;
import com.coderslab.crm.model.Privilege;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.DepartmentRepository;
import com.coderslab.crm.repository.PrivilegeRepository;
import com.coderslab.crm.specification.DepartmentSpecification;
import com.coderslab.crm.specification.SearchCriteria;
import com.coderslab.crm.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    PrivilegeRepository privilegeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, PrivilegeRepository privilegeRepository) {
        this.departmentRepository = departmentRepository;
        this.privilegeRepository = privilegeRepository;
    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id){
        return departmentRepository.getById(id);
    }

    public Page<Department> findDepartmentsBySearchWithPaginationAndSorting(int pageNo, int pageSize, String sortField, String sortDirection, DepartmentFilter departmentFilter){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        DepartmentSpecification spec1 = new DepartmentSpecification(new SearchCriteria("name",":",departmentFilter.getName()));
        DepartmentSpecification spec2 = new DepartmentSpecification(new SearchCriteria("description",":",departmentFilter.getDescription()));;

        return this.departmentRepository.findAll(Specification.where(spec1).and(spec2), pageable);
    }

    public void addDepartment(Department department){
        Privilege privilege = privilegeRepository.findBySalesAndServiceAndAccountancy(department.getPrivilege().getSales(),department.getPrivilege().getService(),department.getPrivilege().getAccountancy());
        if(privilege == null){
            privilegeRepository.save(department.getPrivilege());
        }
        else{
            department.setPrivilege(privilege);
        }
        departmentRepository.save(department);
    }

    public void deleteDepartmentById(Long id){
        departmentRepository.delete(departmentRepository.getById(id));
    }

    public void editDepartment(Department department){
        departmentRepository.save(department);
    }


}
