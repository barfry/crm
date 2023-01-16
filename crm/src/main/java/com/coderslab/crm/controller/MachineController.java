package com.coderslab.crm.controller;

import com.coderslab.crm.filter.MachineFilter;
import com.coderslab.crm.model.Machine;
import com.coderslab.crm.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

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
    public String showAllMachinesPage(Model model){
        model.addAttribute("machines", machineService.getAllMachines());
        model.addAttribute("machineFilter", new MachineFilter());

        return "user-zone/all-machines";
    }



}
