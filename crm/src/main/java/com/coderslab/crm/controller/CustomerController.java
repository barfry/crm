package com.coderslab.crm.controller;

import com.coderslab.crm.filter.CustomerFilter;
import com.coderslab.crm.model.*;
import com.coderslab.crm.service.*;
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
    ContactPersonService contactPersonService;
    MachineService machineService;
    TypeService typeService;
    InquiryService inquiryService;
    UserService userService;
    EventService eventService;
    RoleService roleService;
    InterventionService interventionService;
    ManufacturerService manufacturerService;

    public CustomerController(CustomerService customerService, CategoryService categoryService, ContactPersonService contactPersonService, MachineService machineService, TypeService typeService, InquiryService inquiryService, UserService userService, EventService eventService, RoleService roleService, InterventionService interventionService, ManufacturerService manufacturerService) {
        this.customerService = customerService;
        this.categoryService = categoryService;
        this.contactPersonService = contactPersonService;
        this.machineService = machineService;
        this.typeService = typeService;
        this.inquiryService = inquiryService;
        this.userService = userService;
        this.eventService = eventService;
        this.roleService = roleService;
        this.interventionService = interventionService;
        this.manufacturerService = manufacturerService;
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

        customerService.addNewCustomer(customer);

        return "redirect:/customers";
    }

    @GetMapping("/customer-details")
    public String customerDetailsPage(@RequestParam(value = "customerId") Long customerId, Model model){

        model.addAttribute("customer", customerService.getCustomerById(customerId));
        model.addAttribute("countInquiries", inquiryService.countInquiriesByCustomerId(customerId));
        model.addAttribute("countInterventions", interventionService.countInterventionByCustomerId(customerId));
        model.addAttribute("currentUserId", userService.getCurrentUser().getId());
        model.addAttribute("currentUserPrivileges", userService.getCurrentUser().getDepartment().getPrivilege());

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

        customerService.editCustomer(customer);

        return "redirect:/customers/customer-details?customerId=" + customer.getId();
    }

    @GetMapping("/customer-details/add-new-contact")
    public String initAddNewContactPage(@RequestParam(value = "customerId") Long customerId, Model model){
        model.addAttribute("customerId", customerId);
        model.addAttribute("contactPerson", new ContactPerson());

        return "/user-zone/add-new-contact";
    }

    @PostMapping("/customer-details/add-new-contact")
    public String addNewContact(@RequestParam(value = "customerId") Long customerId, @Valid ContactPerson contactPerson, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("customerId", customerId);
            model.addAttribute("contactPerson", contactPerson);

            return "/user-zone/add-new-contact";
        }

        contactPersonService.addNewContactPersonToCustomer(contactPerson, customerService.getCustomerById(customerId));

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @GetMapping("/customer-details/edit-contact")
    public String initEditContactPage(@RequestParam(value = "contactId") Long contactId, @RequestParam(value = "customerId") Long customerId, Model model){
        model.addAttribute("customerId", customerId);
        model.addAttribute("contactPerson", contactPersonService.getContactPersonById(contactId));

        return "/user-zone/edit-contact";
    }

    @PostMapping("/customer-details/edit-contact")
    public String editContact(@RequestParam(value = "customerId") Long customerId, @Valid ContactPerson contactPerson, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("customerId", customerId);
            model.addAttribute("contactPerson", contactPerson);

            return "/user-zone/edit-contact";
        }

        contactPersonService.editContactPerson(contactPerson);

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @PostMapping("/customer-details/disable-contact")
    public String disableContact(@RequestParam(value = "customerId") Long customerId, @RequestParam(value = "contactId") Long contactId, Model model){

        contactPersonService.disableContactPerson(contactId);

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @PostMapping("/customer-details/activate-contact")
    public String activateContact(@RequestParam(value = "customerId") Long customerId, @RequestParam(value = "contactId") Long contactId, Model model){

        contactPersonService.activateContactPerson(contactId);

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @GetMapping("/customer-details/add-new-machine")
    public String initAddNewMachinePage(@RequestParam(value = "customerId") Long customerId, Model model){
        model.addAttribute("customerId", customerId);
        model.addAttribute("machine", new Machine());
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());

        return "/user-zone/add-new-machine";
    }

    @PostMapping("/customer-details/add-new-machine")
    public String addNewMachine(@RequestParam(value = "customerId") Long customerId, @Valid Machine machine, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("customerId", customerId);
            model.addAttribute("machine", machine);
            model.addAttribute("types", typeService.getAllTypes());
            model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());

            return "/user-zone/add-new-machine";
        }

        machineService.addNewMachine(machine, customerService.getCustomerById(customerId));

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @GetMapping("/customer-details/add-new-inquiry")
    public String initAddNewInquiryPage(@RequestParam(value = "customerId") Long customerId, Model model){
        model.addAttribute("inquiry", new Inquiry());
        model.addAttribute("machines", machineService.getMachinesByCustomerId(customerId));
        model.addAttribute("customerId", customerId);

        return "/user-zone/add-new-inquiry";
    }

    @PostMapping("/customer-details/add-new-inquiry")
    public String addNewInquiry(@RequestParam(value = "customerId") Long customerId, @Valid Inquiry inquiry, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("inquiry",inquiry);
            model.addAttribute("machines", machineService.getMachinesByCustomerId(customerId));
            model.addAttribute("customerId", customerId);
            return "/user-zone/add-new-inquiry";
        }

        inquiryService.addNewInquiry(inquiry, customerId);

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @GetMapping("/customer-details/edit-inquiry")
    public String initEditInquiryPage(@RequestParam(value = "customerId") Long customerId, @RequestParam(value = "inquiryId") Long inquiryId, Model model){
        model.addAttribute("inquiry", inquiryService.getInquiryById(inquiryId));
        model.addAttribute("machines", machineService.getMachinesByCustomerId(customerId));
        model.addAttribute("customerId", customerId);

        return "/user-zone/edit-inquiry";
    }

    @PostMapping("/customer-details/edit-inquiry")
    public String editInquiry(@RequestParam(name = "customerId") Long customerId ,@Valid Inquiry inquiry, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("inquiry", inquiry);
            model.addAttribute("machines", machineService.getMachinesByCustomerId(inquiry.getCustomer().getId()));
            return "/user-zone/edit-inquiry";
        }

        inquiryService.editInquiry(inquiry);

        return "redirect:/customers/customer-details?customerId=" +customerId;
    }

    @GetMapping("/customer-details/add-new-event")
    public String initAddNewEventPage(@RequestParam(name = "customerId") Long customerId, Model model){
        model.addAttribute("event", new Event());
        model.addAttribute("mainUser", userService.getCurrentUser());
        model.addAttribute("customerId", customerId);
        model.addAttribute("users", userService.getActiveUsers());

        return "/user-zone/add-new-event";
    }

    @PostMapping("/customer-details/add-new-event")
    public String addNewEventPage(@RequestParam(name = "customerId") Long customerId, @Valid Event event, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("event", event);
            model.addAttribute("customerId", customerId);
            model.addAttribute("users", userService.getActiveUsers());

            return "/user-zone/add-new-event";
        }

        eventService.addNewEvent(event, customerId);

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @GetMapping("/customer-details/edit-event")
    public String initEditEventPage(@RequestParam(name = "customerId") Long customerId, @RequestParam(name = "eventId") Long eventId, Model model){

        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("mainUser", userService.getCurrentUser());
        model.addAttribute("customerId", customerId);
        model.addAttribute("users", userService.getActiveUsers());

        return "/user-zone/edit-event";
    }

    @PostMapping("/customer-details/edit-event")
    public String editEvent(@RequestParam(name = "customerId") Long customerId, @Valid Event event, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("event", event);
            model.addAttribute("mainUser", userService.getCurrentUser());
            model.addAttribute("customerId", customerId);
            model.addAttribute("users", userService.getActiveUsers());

            return "/user-zone/edit-event";
        }

        eventService.editEvent(event);

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @GetMapping("/customer-details/complete-event")
    public String initCompleteEventPage(@RequestParam(name = "eventId") Long eventId, @RequestParam(name = "customerId") Long customerId, Model model){
        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("customerId", customerId);
        return "/user-zone/complete-event";
    }

    @PostMapping("/customer-details/complete-event")
    public String completeEvent(@RequestParam(name = "customerId") Long customerId, @Valid Event event, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("event", event);
            model.addAttribute("customerId", customerId);
            return "/user-zone/complete-event";
        }

        eventService.completeEvent(event);

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @PostMapping("/customer-details/activate-event")
    public String activateEvent(@RequestParam(value = "customerId") Long customerId, @RequestParam(value = "eventId") Long eventId, Model model) {

        eventService.activateEvent(eventId);

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @GetMapping("/customer-details/add-new-intervention")
    public String initAddNewInterventionPage(@RequestParam(name = "customerId") Long customerId, Model model){
        model.addAttribute("intervention", new Intervention());
        model.addAttribute("machines", machineService.getMachinesByCustomerId(customerId));
        model.addAttribute("customerId", customerId);
        model.addAttribute("service", userService.usersWithServicePrivilege());

        return "/user-zone/add-new-intervention";
    }

    @PostMapping("/customer-details/add-new-intervention")
    public String addNewIntervention(@RequestParam(name = "customerId") Long customerId, @Valid Intervention intervention, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("intervention", intervention);
            model.addAttribute("machines", machineService.getMachinesByCustomerId(customerId));
            model.addAttribute("customerId", customerId);
            model.addAttribute("service", userService.usersWithServicePrivilege());
            return "/user-zone/add-new-intervention";
        }

        interventionService.addNewIntervention(intervention);

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }

    @GetMapping("/customer-details/edit-intervention")
    public String initEditInterventionPage(@RequestParam(name = "customerId") Long customerId, @RequestParam(name = "interventionId") Long interventionId, Model model){
        model.addAttribute("intervention", interventionService.getInterventionById(interventionId));
        model.addAttribute("customerId", customerId);
        model.addAttribute("service", userService.usersWithServicePrivilege());

        return "/user-zone/edit-intervention";
    }

    @PostMapping("/customer-details/edit-intervention")
    public String editIntervention(@RequestParam(name = "customerId") Long customerId, @Valid Intervention intervention, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("intervention", intervention);
            model.addAttribute("machines", machineService.getMachinesByCustomerId(customerId));
            model.addAttribute("customerId", customerId);
            model.addAttribute("service", userService.usersWithServicePrivilege());

            return "/user-zone/add-new-intervention";
        }

        interventionService.editIntervention(intervention);

        return "redirect:/customers/customer-details?customerId=" + customerId;
    }
}
