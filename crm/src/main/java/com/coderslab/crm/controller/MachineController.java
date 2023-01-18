package com.coderslab.crm.controller;

import com.coderslab.crm.filter.MachineFilter;
import com.coderslab.crm.filter.ManufacturerFilter;
import com.coderslab.crm.model.Machine;
import com.coderslab.crm.model.Manufacturer;
import com.coderslab.crm.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/machines")
@SessionAttributes("machineFilter")
public class MachineController {

    @Autowired
    MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping("")
    public String showAllMachines(Model model){
        MachineFilter machineFilter = new MachineFilter();
        model.addAttribute("machines", machineService.getAllMachines());

        return  showAllMachinesPage(machineFilter,1,"id","asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String showAllMachinesPage(@ModelAttribute MachineFilter machineFilter, @PathVariable(value = "pageNo") int pageNo,
                                           @RequestParam(value = "sortField", defaultValue = "id") String sortField, @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                           Model model){
        int pageSize = 5;

        Page<Machine> page = machineService.findMachinesBySearchWithPaginationAndSorting(pageNo, pageSize, sortField, sortDir, machineFilter);
        List<Machine> listMachines = page.getContent();

        model.addAttribute("machineFilter", machineFilter);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("machines", listMachines);

        return "user-zone/all-machines";
    }

    @GetMapping("/machine-details")
    public String showMachineDetailsPage(@RequestParam(value = "machineId") Long machineId, Model model){
        model.addAttribute("machine", machineService.getMachineById(machineId));

        return "user-zone/machine-details";
    }


}
