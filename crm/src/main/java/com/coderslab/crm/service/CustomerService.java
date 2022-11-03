package com.coderslab.crm.service;

import com.coderslab.crm.filter.CustomerFilter;
import com.coderslab.crm.filter.UserFilter;
import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.CustomerRepository;
import com.coderslab.crm.specification.CustomerSpecification;
import com.coderslab.crm.specification.SearchCriteria;
import com.coderslab.crm.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    UserService userService;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id){
        return customerRepository.getById(id);
    }

    public void addNewCustomer(Customer customer){
        customer.setUpdateDate(LocalDate.now());
        customer.setModifier(userService.getCurrentUser());

        customerRepository.save(customer);
    }

    public Page<Customer> findCustomersBySearchWithPaginationAndSorting(int pageNo, int pageSize, String sortField, String sortDirection, CustomerFilter customerFilter){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        CustomerSpecification spec1 = new CustomerSpecification(new SearchCriteria("name",":",customerFilter.getName()));
        CustomerSpecification spec2 = new CustomerSpecification(new SearchCriteria("city",":",customerFilter.getCity()));
        CustomerSpecification spec3 = new CustomerSpecification(new SearchCriteria("street",":",customerFilter.getStreet()));
        CustomerSpecification spec4 = new CustomerSpecification(new SearchCriteria("streetNumber",":",customerFilter.getStreetNumber()));
        CustomerSpecification spec5 = new CustomerSpecification(new SearchCriteria("zipCode",":",customerFilter.getZipCode()));
        CustomerSpecification spec6 = new CustomerSpecification(new SearchCriteria("province",":",customerFilter.getProvince()));
        CustomerSpecification spec7 = new CustomerSpecification(new SearchCriteria("taxCode",":",customerFilter.getTaxCode()));

        return this.customerRepository.findAll(Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5).and(spec6).and(spec7), pageable);
    }



}
