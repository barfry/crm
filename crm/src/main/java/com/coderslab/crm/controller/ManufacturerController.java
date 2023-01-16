package com.coderslab.crm.controller;

import com.coderslab.crm.filter.CustomerFilter;
import com.coderslab.crm.filter.ManufacturerFilter;
import com.coderslab.crm.model.ContactPerson;
import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.Manufacturer;
import com.coderslab.crm.model.Type;
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
@RequestMapping("/manufacturers")
@SessionAttributes("manufacturerFilter")
public class ManufacturerController {

    @Autowired
    ManufacturerService manufacturerService;
    CategoryService categoryService;
    TypeService typeService;
    ContactPersonService contactPersonService;
    InterventionService interventionService;
    MachineService machineService;

    public ManufacturerController(ManufacturerService manufacturerService, CategoryService categoryService, TypeService typeService, ContactPersonService contactPersonService, InterventionService interventionService, MachineService machineService) {
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
        this.typeService = typeService;
        this.contactPersonService = contactPersonService;
        this.interventionService = interventionService;
        this.machineService = machineService;
    }

    @GetMapping("")
    public String showAllManufacturers(Model model){
        ManufacturerFilter manufacturerFilter = new ManufacturerFilter();
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());

        return  showAllManufacturersPage(manufacturerFilter,1,"id","asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String showAllManufacturersPage(@ModelAttribute ManufacturerFilter manufacturerFilter, @PathVariable(value = "pageNo") int pageNo,
                                           @RequestParam(value = "sortField", defaultValue = "id") String sortField, @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                           Model model){
        int pageSize = 5;

        Page<Manufacturer> page = manufacturerService.findManufacturersBySearchWithPaginationAndSorting(pageNo, pageSize, sortField, sortDir, manufacturerFilter);
        List<Manufacturer> listManufacturers = page.getContent();

        model.addAttribute("manufacturerFilter", manufacturerFilter);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("manufacturers", listManufacturers);

        return "user-zone/all-manufacturers";
    }

    @GetMapping("/add-new-manufacturer")
    public String initAddNewManufacturerPage(Model model){

        model.addAttribute("manufacturer", new Manufacturer());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "user-zone/add-new-manufacturer";
    }

    @PostMapping("/add-new-manufacturer")
    public String addNewManufacturer(@Valid Manufacturer manufacturer, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("manufacturer", new Manufacturer());
            model.addAttribute("categories", categoryService.getAllCategories());

            return "user-zone/add-new-manufacturer";
        }

        manufacturerService.addNewManufacturer(manufacturer);

        return "redirect:/manufacturers";
    }

    @GetMapping("/manufacturer-details")
    public String manufacturerDetailsPage(@RequestParam(name = "manufacturerId") Long manufacturerId, Model model){
        model.addAttribute("manufacturer", manufacturerService.getManufacturerById(manufacturerId));
        model.addAttribute("upcomingInstallations", interventionService.getPlannedInterventionsByManufacturerAndByInquiryType(manufacturerId, "INSTALLATION"));
        model.addAttribute("machines", machineService.getMachinesByManufacturerId(manufacturerId));

        return "user-zone/manufacturer-details";
    }

    @GetMapping("/manufacturer-details/add-new-type")
    public String initAddNewTypePage(@RequestParam(name = "manufacturerId") Long manufacturerId, Model model){
        model.addAttribute("type", new Type());
        model.addAttribute("manufacturerId", manufacturerId);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "user-zone/add-new-type";
    }

    @PostMapping("/manufacturer-details/add-new-type")
    public String addNewType(@RequestParam(name = "manufacturerId") Long manufacturerId ,@Valid Type type, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("type", type);
            model.addAttribute("manufacturerId", manufacturerId);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "user-zone/add-new-type";
        }
        type.setManufacturer(manufacturerService.getManufacturerById(manufacturerId));
        typeService.addNewType(type);

        return "redirect:/manufacturers/manufacturer-details?manufacturerId=" + manufacturerId;
    }

    @GetMapping("/manufacturer-details/edit-type")
    public String initEditTypePage(@RequestParam(name = "typeId") Long typeId, Model model){
        model.addAttribute("type", typeService.getTypeById(typeId));
        model.addAttribute("categories", categoryService.getAllCategories());

        return "user-zone/edit-type";
    }

    @PostMapping("/manufacturer-details/edit-type")
    public String editType(@Valid Type type, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("type", type);
            model.addAttribute("categories", categoryService.getAllCategories());
        }

        typeService.editType(type);

        return "redirect:/manufacturers/manufacturer-details?manufacturerId=" + type.getManufacturer().getId();
    }

    @GetMapping("/manufacturer-details/add-new-contact")
    public String initAddNewContact(@RequestParam(name = "manufacturerId") Long manufacturerId, Model model){
        model.addAttribute("contactPerson", new ContactPerson());
        model.addAttribute("manufacturerId", manufacturerId);

        return "user-zone/add-new-manufacturer-contact";
    }

    @PostMapping("/manufacturer-details/add-new-contact")
    public String addNewContact(@RequestParam(name = "manufacturerId") Long manufacturerId, @Valid ContactPerson contactPerson, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("contactPerson", contactPerson);
            model.addAttribute("manufacturerId", manufacturerId);

            return "user-zone/add-new-manufacturer-contact";
        }

        contactPersonService.addNewContactPersonToManufacturer(contactPerson, manufacturerService.getManufacturerById(manufacturerId));

        return "redirect:/manufacturers/manufacturer-details?manufacturerId=" + manufacturerId;
    }

    @GetMapping("/manufacturer-details/edit-contact")
    public String initEditContactPage(@RequestParam(name = "manufacturerId") Long manufacturerId, @RequestParam(name = "contactPersonId") Long contactPersonId, Model model){
        model.addAttribute("manufacturerId", manufacturerId);
        model.addAttribute("contactPerson", contactPersonService.getContactPersonById(contactPersonId));

        return "user-zone/edit-manufacturer-contact";
    }

    @PostMapping("/manufacturer-details/edit-contact")
    public String editContact(@RequestParam(name = "manufacturerId") Long manufacturerId, @Valid ContactPerson contactPerson, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("manufacturerId", manufacturerId);
            model.addAttribute("contactPerson", contactPerson);

            return "user-zone/edit-manufacturer-contact";
        }

        contactPersonService.editContactPerson(contactPerson);

        return "redirect:/manufacturers/manufacturer-details?manufacturerId=" + manufacturerId;
    }

    @PostMapping("/manufacturer-details/disable-contact")
    public String disableContact(@RequestParam(name = "contactPersonId") Long contactPersonId, @RequestParam(name = "manufacturerId") Long manufacturerId, Model model){

        contactPersonService.disableContactPerson(contactPersonId);

        return "redirect:/manufacturers/manufacturer-details?manufacturerId=" + manufacturerId;
    }

    @PostMapping("/manufacturer-details/activate-contact")
    public String activateContact(@RequestParam(name = "contactPersonId") Long contactPersonId, @RequestParam(name = "manufacturerId") Long manufacturerId, Model model){

        contactPersonService.activateContactPerson(contactPersonId);

        return "redirect:/manufacturers/manufacturer-details?manufacturerId=" + manufacturerId;
    }

    @GetMapping("/manufacturer-details/edit-manufacturer")
    public String initEditManufacturerPage(@RequestParam(name = "manufacturerId") Long manufacturerId, Model model){
        model.addAttribute("manufacturer", manufacturerService.getManufacturerById(manufacturerId));
        model.addAttribute("categories", categoryService.getAllCategories());

        return "user-zone/edit-manufacturer";
    }

    @PostMapping("/manufacturer-details/edit-manufacturer")
    public String editManufacturer(@Valid Manufacturer manufacturer, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("manufacturer", manufacturer);
            model. addAttribute("categories", categoryService.getAllCategories());

            return "user-zone/edit-manufacturer";
        }

        manufacturerService.editManufacturer(manufacturer);

        return "redirect:/manufacturers/manufacturer-details?manufacturerId=" + manufacturer.getId();
    }

}
