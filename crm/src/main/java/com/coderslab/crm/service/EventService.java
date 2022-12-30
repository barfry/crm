package com.coderslab.crm.service;


import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.Event;
import com.coderslab.crm.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;
    UserService userService;
    CustomerService customerService;

    public EventService(EventRepository eventRepository, UserService userService, CustomerService customerService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.customerService = customerService;
    }

    public Event getEventById(Long eventId){
        return eventRepository.getById(eventId);
    }

    public List<Event> getEventsByCustomerId(Long customerId){
        return eventRepository.getEventByCustomerId(customerId);
    }

    public Event addNewEvent(Event event, Long customerId){
        event.setMainUser(userService.getCurrentUser());
        event.setCreationDate(LocalDate.now());
        event.setCustomer(customerService.getCustomerById(customerId));

        return eventRepository.save(event);
    }

    public void editEvent(Event event){
        event.setModifier(userService.getCurrentUser());
        event.setUpdateDate(LocalDate.now());
        eventRepository.updateEvent(event.getType(), event.getAssistingUser(), event.getStart(), event.getEnd(), event.getDescription(), event.getId());
    }

    public void completeEvent(Event event){
        event.setModifier(userService.getCurrentUser());
        event.setUpdateDate(LocalDate.now());
        event.setCommentAfterEvent(event.getCommentAfterEvent());
        event.setActive(false);
        eventRepository.save(event);
    }

    public void activateEvent(Long eventId){
        Event event = eventRepository.getById(eventId);
        event.setActive(true);
        event.setModifier(userService.getCurrentUser());
        event.setUpdateDate(LocalDate.now());
        eventRepository.save(event);
    }

    public List<Event> getActiveEventsByCustomerId(Long customerId){
        return eventRepository.getEventsByCustomerIdAndActiveIsTrue(customerId);
    }

    public List<Event> getInactiveEventsByCustomerId(Long customerId){
        return eventRepository.getEventsByCustomerIdAndActiveIsFalse(customerId);
    }

}
