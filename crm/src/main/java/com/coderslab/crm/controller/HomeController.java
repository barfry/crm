package com.coderslab.crm.controller;

import com.coderslab.crm.service.EventService;
import com.coderslab.crm.service.TaskService;
import com.coderslab.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    UserService userService;
    EventService eventService;
    TaskService taskService;

    public HomeController(UserService userService, EventService eventService, TaskService taskService) {
        this.userService = userService;
        this.eventService = eventService;
        this.taskService = taskService;
    }

    @GetMapping("/user-zone")
    public String showIndexPage(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("events", eventService.getEventsByUser(userService.getCurrentUser()));
        model.addAttribute("tasks", taskService.getActiveTasksByUser(userService.getCurrentUser()));

        return "user-zone/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
