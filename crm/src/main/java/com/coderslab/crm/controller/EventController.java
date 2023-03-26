package com.coderslab.crm.controller;

import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.Event;
import com.coderslab.crm.repository.EventRepository;
import com.coderslab.crm.service.EventService;
import com.coderslab.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    EventService eventService;
    UserService userService;

    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/events")
    public List<Event> events(@RequestParam(name = "customerId") Long customerId){
        return eventService.getEventsByCustomerId(customerId);
    }

    @GetMapping("/active-events")
    public List<Event> activeEventsByCustomerId(@RequestParam(value = "customerId") Long customerId){
        return eventService.getActiveEventsByCustomerId(customerId);
    }

    @GetMapping("/inactive-events")
    public List<Event> inactiveEventsByCustomerId(@RequestParam(value = "customerId") Long customerId){
        return eventService.getInactiveEventsByCustomerId(customerId);
    }

    @GetMapping("/user-active-events")
    public List<Event> activeEventsByUser(){
        return eventService.getActiveEventsByUser(userService.getCurrentUser());
    }

    @GetMapping("/user-inactive-events")
    public List<Event> inactiveEventsByUser(){
        return eventService.getInactiveEventsByUser(userService.getCurrentUser());
    }


}
