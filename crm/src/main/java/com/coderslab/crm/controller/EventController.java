package com.coderslab.crm.controller;

import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.Event;
import com.coderslab.crm.repository.EventRepository;
import com.coderslab.crm.service.EventService;
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

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public List<Event> events(@RequestParam(name = "customerId") Long customerId){
        return eventService.getEventsByCustomerId(customerId);
    }
}
