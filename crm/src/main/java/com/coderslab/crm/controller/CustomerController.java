package com.coderslab.crm.controller;

import com.coderslab.crm.filter.CustomerFilter;
import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.User;
import com.coderslab.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
@SessionAttributes("customerFilter")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("")
    public String showAllCustomers(Model model){
        CustomerFilter customerFilter = new CustomerFilter();
        model.addAttribute("customers", customerService.getAllCustomers());
        return  showAllCustomersPage(customerFilter,1,"id","asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String showAllCustomersPage(@ModelAttribute CustomerFilter customerFilter, @PathVariable(value = "pageNo") int pageNo,
                                       @RequestParam(value = "sortField", defaultValue = "id") String sortField, @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                       Model model){
        int pageSize = 5;

        Page<Customer> page = customerService.findCustomersBySearchWithPaginationAndSorting(pageNo, pageSize, sortField, sortDir, customerFilter);
        List<Customer> listCustomers = page.getContent();

        model.addAttribute("customerFilter", customerFilter);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("customers", listCustomers);

        return "user-zone/all-customers";
    }


}
