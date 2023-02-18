package com.coderslab.crm.controller;

import com.coderslab.crm.model.Intervention;
import com.coderslab.crm.service.InquiryService;
import com.coderslab.crm.service.InterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class InterventionController {

    @Autowired
    InterventionService interventionService;

    public InterventionController(InterventionService interventionService) {
        this.interventionService = interventionService;
    }

    @GetMapping("/all-interventions")
    public List<Intervention> getAllInterventions(){
        return interventionService.getAllInterventions();
    }

    @GetMapping("/interventions")
    public List<Intervention> interventions(@RequestParam(name = "customerId") Long customerId){
        return interventionService.getInterventionsByCustomerId(customerId);
    }

    @GetMapping("/diagnoses-by-customer")
    public List<Intervention> diagnosesByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        return interventionService.getInterventionsByTypeAndCustomerId("DIAGNOSE", customerId);
    }

    @GetMapping("/repairs-by-customer")
    public List<Intervention> repairsByCustomerId(@RequestParam(name = "customerId") Long customerId){
        return interventionService.getInterventionsByTypeAndCustomerId("REPAIR", customerId);
    }

    @GetMapping("/installations-by-customer")
    public List<Intervention> installationsByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        return interventionService.getInterventionsByTypeAndCustomerId("INSTALLATION", customerId);
    }

    @GetMapping("/deinstallations-by-customer")
    public List<Intervention> deinstallationsByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        return interventionService.getInterventionsByTypeAndCustomerId("DEINSTALLATION", customerId);
    }

    @GetMapping("/trainings-by-customer")
    public List<Intervention> trainingsByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        return interventionService.getInterventionsByTypeAndCustomerId("TRAINING", customerId);
    }

    @GetMapping("/reviews-by-customer")
    public List<Intervention> reviewsByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        return interventionService.getInterventionsByTypeAndCustomerId("TECHNICAL REVIEW", customerId);
    }

    @GetMapping("/planned-interventions")
    public List<Intervention> plannedInterventionsByCustomerId(@RequestParam(name = "customerId") Long customerId){
        return interventionService.getInterventionsByCustomerIdWhereConfirmedIsFalse(customerId);
    }

    @GetMapping("/confirmed-interventions")
    public List<Intervention> confirmedInterventionsByCustomerId(@RequestParam(name = "customerId") Long customerId){
        return interventionService.getInterventionsByCustomerIdWhereConfirmedIsTrue(customerId);
    }

    

}
