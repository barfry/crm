package com.coderslab.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/service-schedule")
public class ScheduleController {

    @GetMapping("")
    public String showServiceSchedulePage(Model model){
        return "user-zone/service-schedule";
    }
}
