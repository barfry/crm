package com.coderslab.crm.controller;

import com.coderslab.crm.filter.InquiryFilter;
import com.coderslab.crm.filter.InterventionFilter;
import com.coderslab.crm.model.Inquiry;
import com.coderslab.crm.model.Intervention;
import com.coderslab.crm.service.InquiryService;
import com.coderslab.crm.service.InterventionService;
import com.coderslab.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("")
@SessionAttributes({"interventionFilter"})
public class InterventionController {

    @Autowired
    InterventionService interventionService;
    UserService userService;

    public InterventionController(InterventionService interventionService, UserService userService) {
        this.interventionService = interventionService;
        this.userService = userService;
    }

    @GetMapping("/all-interventions")
    @ResponseBody
    public List<Intervention> getAllInterventions(){
        return interventionService.getAllInterventions();
    }

    @GetMapping("/diagnoses-by-customer")
    @ResponseBody
    public List<Intervention> diagnosesByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        return interventionService.getInterventionsByTypeAndCustomerId("DIAGNOSE", customerId);
    }

    @GetMapping("/repairs-by-customer")
    @ResponseBody
    public List<Intervention> repairsByCustomerId(@RequestParam(name = "customerId") Long customerId){
        return interventionService.getInterventionsByTypeAndCustomerId("REPAIR", customerId);
    }

    @GetMapping("/installations-by-customer")
    @ResponseBody
    public List<Intervention> installationsByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        return interventionService.getInterventionsByTypeAndCustomerId("INSTALLATION", customerId);
    }

    @GetMapping("/deinstallations-by-customer")
    @ResponseBody
    public List<Intervention> deinstallationsByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        return interventionService.getInterventionsByTypeAndCustomerId("DEINSTALLATION", customerId);
    }

    @GetMapping("/trainings-by-customer")
    @ResponseBody
    public List<Intervention> trainingsByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        return interventionService.getInterventionsByTypeAndCustomerId("TRAINING", customerId);
    }

    @GetMapping("/reviews-by-customer")
    @ResponseBody
    public List<Intervention> reviewsByCustomerId(@RequestParam(name = "customerId") Long customerId) {
        return interventionService.getInterventionsByTypeAndCustomerId("TECHNICAL REVIEW", customerId);
    }

    @GetMapping("/planned-interventions")
    @ResponseBody
    public List<Intervention> plannedInterventionsByCustomerId(@RequestParam(name = "customerId") Long customerId){
        return interventionService.getInterventionsByCustomerIdWhereConfirmedIsFalse(customerId);
    }

    @GetMapping("/confirmed-interventions")
    @ResponseBody
    public List<Intervention> confirmedInterventionsByCustomerId(@RequestParam(name = "customerId") Long customerId){
        return interventionService.getInterventionsByCustomerIdWhereConfirmedIsTrue(customerId);
    }

    @GetMapping("/all-confirmed-interventions")
    @ResponseBody
    public List<Intervention> allConfirmedInterventions(){
        return interventionService.getAllConfirmedInterventions();
    }

    @GetMapping("/all-planned-interventions")
    @ResponseBody
    public List<Intervention> allPlannedInterventions(){
        return interventionService.getAllPlannedInterventions();
    }


    @GetMapping("/interventions")
    public String showAllInterventions(Model model) {
        InterventionFilter interventionFilter = new InterventionFilter();
        model.addAttribute("interventionList", interventionService.getAllInterventions());

        return showAllInterventionsPage(interventionFilter, 1, "id", "asc", model);
    }

    @GetMapping("/interventions/page/{pageNo}")
    public String showAllInterventionsPage(@ModelAttribute InterventionFilter interventionFilter, @PathVariable(value = "pageNo") int pageNo,
                                       @RequestParam(value = "sortField", defaultValue = "id") String sortField, @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir, Model model) {

        int pageSize = 10;

        Page<Intervention> page = interventionService.findInterventionsBySearchWithPaginationAndSorting(pageNo, pageSize, sortField, sortDir, interventionFilter);
        List<Intervention> listInterventions = page.getContent();

        model.addAttribute("interventionFilter", interventionFilter);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("interventionList", listInterventions);

        return "user-zone/all-interventions";
    }

    @GetMapping("/interventions/intervention-details")
    public String showInterventionDetailsPage(@RequestParam(name = "interventionId") Long interventionId, Model model){
        model.addAttribute("intervention", interventionService.getInterventionById(interventionId));
        return "user-zone/intervention-details";
    }

    @GetMapping("/interventions/intervention-details/edit-intervention")
    public String initEditInterventionPage(@RequestParam(name = "interventionId") Long interventionId, Model model){
        model.addAttribute("intervention", interventionService.getInterventionById(interventionId));
        model.addAttribute("technicians", userService.getAllTechnicians());
        model.addAttribute("actionUrl", "/interventions/intervention-details/edit-intervention");
        return "user-zone/edit-intervention";
    }

    @PostMapping("/interventions/intervention-details/edit-intervention")
    public String editIntervention(@Valid Intervention intervention, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("intervention", intervention);
            model.addAttribute("technicians", userService.getAllTechnicians());
            model.addAttribute("actionUrl", "/interventions/intervention-details/edit-intervention");

            return "/user-zone/edit-intervention";
        }

        interventionService.editIntervention(intervention);

        return "redirect:/interventions/intervention-details?interventionId=" + intervention.getId();
    }

    @PostMapping("/interventions/intervention-details/remove-intervention")
    public String removeIntervention(@RequestParam(name = "interventionId") Long interventionId, @RequestParam(name = "inquiryId") Long inquiryId, Model model){
        interventionService.removeInterventionById(interventionId);

        return "redirect:/inquiries/inquiry-details?inquiryId=" + inquiryId;
    }

    @PostMapping("/interventions/intervention-details/confirm-intervention")
    public String confirmIntervention(@RequestParam(name = "interventionId") Long interventionId, Model model){
        interventionService.confirmInterventionById(interventionId);

        return "redirect:/interventions/intervention-details?interventionId=" + interventionId;
    }



}
