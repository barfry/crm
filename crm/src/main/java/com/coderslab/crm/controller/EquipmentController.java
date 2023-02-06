package com.coderslab.crm.controller;

import com.coderslab.crm.filter.EquipmentFilter;
import com.coderslab.crm.model.Equipment;
import com.coderslab.crm.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/all-equipment")
public class EquipmentController {

    @Autowired
    EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("")
    public String showAllEquipment(Model model){
        EquipmentFilter equipmentFilter = new EquipmentFilter();
        model.addAttribute("equipmentList", equipmentService.getAllEquipment());

        return  initAddEquipmentPage(equipmentFilter,1,"id","asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String initAddEquipmentPage(@ModelAttribute EquipmentFilter equipmentFilter, @PathVariable(value = "pageNo") int pageNo,
                                       @RequestParam(value = "sortField", defaultValue = "id") String sortField, @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir, Model model){

        int pageSize = 10;

        Page<Equipment> page = equipmentService.findEquipmentBySearchWithPaginationAndSorting(pageNo, pageSize, sortField, sortDir, equipmentFilter);
        List<Equipment> listEquipment = page.getContent();

        model.addAttribute("equipmentFilter", equipmentFilter);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("equipmentList", listEquipment);

        return "user-zone/all-equipment";
    }

    @GetMapping("/add-new-equipment")
    public String initAddNewEquipmentPage(Model model){
        model.addAttribute("equipment", new Equipment());

        return "user-zone/add-new-eqipment";
    }

    @PostMapping("/add-new-equipment")
    public String addNewEquipment(@Valid Equipment equipment, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("equipment", equipment);

            return "user-zone/add-new-equipment";
        }

        equipmentService.addNewOrEditEquipment(equipment);

        return "redirect:";
    }

    @GetMapping("/edit-equipment")
    public String initEditEquipmentPage(@RequestParam(name="equipmentId") Long equipmentId ,Model model){
        model.addAttribute("equipment", equipmentService.getEquipmentById(equipmentId));

        return "user-zone/edit-equipment";
    }

    @PostMapping("/edit-equipment")
    public String editEquipment(@Valid Equipment equipment, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("equipment", equipment);

            return "user-zone/edit-equipment";
        }

        equipmentService.addNewOrEditEquipment(equipment);

        return "redirect:";
    }
}
