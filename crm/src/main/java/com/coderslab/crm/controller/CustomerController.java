package com.coderslab.crm.controller;

import com.coderslab.crm.filter.CustomerFilter;
import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.User;
import com.coderslab.crm.service.CategoryService;
import com.coderslab.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customers")
@SessionAttributes("customerFilter")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    CategoryService categoryService;

    public CustomerController(CustomerService customerService, CategoryService categoryService) {
        this.customerService = customerService;
        this.categoryService = categoryService;
    }

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

    @GetMapping("/add-new-customer")
    public String initAddNewCustomerPage(Model model){

        model.addAttribute("customer", new Customer());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "user-zone/add-new-customer";
    }

    @PostMapping("/add-new-customer")
    public String addNewCustomer(@Valid Customer customer, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("customer", customer);
            model.addAttribute("categories", categoryService.getAllCategories());

            return "user-zone/add-new-customer";
        }

        customerService.addNewOrUpdateCustomer(customer);

        return "redirect:/customers";
    }

    @GetMapping("/customer-details")
    public String customerDetailsPage(@RequestParam(value = "customerId") Long customerId, Model model){

        model.addAttribute("customer", customerService.getCustomerById(customerId));

        return "user-zone/customer-details";
    }

    @GetMapping("/customer-details/edit-customer")
    public String initEditCustomerPage(@RequestParam(value = "customerId") Long customerId, Model model){
        model.addAttribute("customer", customerService.getCustomerById(customerId));
        model.addAttribute("categories", categoryService.getAllCategories());

        return "/user-zone/edit-customer";
    }

    @PostMapping("/customer-details/edit-customer")
    public String editCustomer(@Valid Customer customer, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("customer", customer);
            model.addAttribute("categories", categoryService.getAllCategories());

            return "/user-zone/edit-customer";
        }

        customerService.addNewOrUpdateCustomer(customer);

        return "redirect:/customers/customer-details?customerId=" + customer.getId();
    }
}
