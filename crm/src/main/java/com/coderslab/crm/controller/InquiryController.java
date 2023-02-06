package com.coderslab.crm.controller;

import com.coderslab.crm.filter.EquipmentFilter;
import com.coderslab.crm.filter.InquiryFilter;
import com.coderslab.crm.model.Equipment;
import com.coderslab.crm.model.Inquiry;
import com.coderslab.crm.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inquiries")
public class InquiryController {

    @Autowired
    InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @GetMapping("")
    public String showAllInquiries(Model model){
        InquiryFilter inquiryFilter = new InquiryFilter();
        model.addAttribute("inquiryList", inquiryService.getAllInquiries());

        return  initAddEquipmentPage(inquiryFilter,1,"id","asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String initAddEquipmentPage(@ModelAttribute InquiryFilter inquiryFilter, @PathVariable(value = "pageNo") int pageNo,
                                       @RequestParam(value = "sortField", defaultValue = "id") String sortField, @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir, Model model){

        int pageSize = 10;

        Page<Inquiry> page = inquiryService.findInquiriesBySearchWithPaginationAndSorting(pageNo, pageSize, sortField, sortDir, inquiryFilter);
        List<Inquiry> listInquiries = page.getContent();

        model.addAttribute("inquiryFilter", inquiryFilter);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("inquiryList", listInquiries);

        return "user-zone/all-inquiries";
    }
}
