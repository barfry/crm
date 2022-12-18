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

    public void addNewIntervention(Intervention intervention){

        Inquiry inquiry = inquiryRepository.getById(intervention.getInquiry().getId());
        inquiry.getInterventionList().add(intervention);
        interventionRepository.save(intervention);
        inquiryRepository.save(inquiry);
    }

    public Intervention getInterventionById(Long interventionId){
        return interventionRepository.getById(interventionId);
    }

    public void editIntervention(Intervention intervention){
        interventionRepository.updateIntervention(intervention.getTechnician(), intervention.getAssistant(), intervention.getAssistant2(), intervention.getStart(), intervention.getEnd(), intervention.getConfirmed(), intervention.getSpareParts(), intervention.getId());
    }

    public Integer countInterventionByCustomerId(Long customerId){
        return interventionRepository.countInterventionsByInquiry_CustomerId(customerId);
    }
}
