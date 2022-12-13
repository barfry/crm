package com.coderslab.crm.service;

import com.coderslab.crm.model.Inquiry;
import com.coderslab.crm.model.Intervention;
import com.coderslab.crm.repository.InterventionRepository;
import com.coderslab.crm.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterventionService {

    @Autowired
    InterventionRepository interventionRepository;
    InquiryRepository inquiryRepository;

    public InterventionService(InterventionRepository interventionRepository, InquiryRepository inquiryRepository) {
        this.interventionRepository = interventionRepository;
        this.inquiryRepository = inquiryRepository;
    }

    public void addNewIntervention(Intervention intervention, String inquiryId){
        Long id = Long.parseLong(inquiryId.substring(inquiryId.lastIndexOf('-') + 1));

        Inquiry inquiry = inquiryRepository.getById(id);
        inquiry.getInterventionList().add(intervention);

        intervention.setInquiry(inquiry);
        interventionRepository.save(intervention);
        inquiryRepository.save(inquiry);
    }
}
