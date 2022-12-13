package com.coderslab.crm.controller;

import com.coderslab.crm.service.InquiryService;
import com.coderslab.crm.service.InterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterventionController {

    @Autowired
    InterventionService interventionService;
    InquiryService inquiryService;

}
