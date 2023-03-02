package com.coderslab.crm.service;

import com.coderslab.crm.filter.InquiryFilter;
import com.coderslab.crm.filter.InterventionFilter;
import com.coderslab.crm.model.Inquiry;
import com.coderslab.crm.model.Intervention;
import com.coderslab.crm.model.Manufacturer;
import com.coderslab.crm.repository.InterventionRepository;
import com.coderslab.crm.repository.InquiryRepository;
import com.coderslab.crm.specification.InquirySpecification;
import com.coderslab.crm.specification.InterventionSpecification;
import com.coderslab.crm.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        interventionRepository.updateIntervention(intervention.getTechnician(), intervention.getAssistant(), intervention.getAssistant2(), intervention.getStart(), intervention.getEnd(), intervention.getSpareParts(), intervention.getNotice(), intervention.getId());
    }

    public Integer countInterventionByCustomerId(Long customerId){
        return interventionRepository.countInterventionsByInquiry_CustomerId(customerId);
    }

    public List<Intervention> getInterventionsByCustomerId(Long customerId){
        return interventionRepository.getInterventionsByInquiry_CustomerId(customerId);
    }

    public List<Intervention> getInterventionsByTypeAndCustomerId(String type, Long customerId){
        return interventionRepository.getInterventionsByInquiry_InquiryTypeAndInquiry_CustomerId(type, customerId);
    }

    public List<Intervention> getInterventionsByCustomerIdWhereConfirmedIsFalse(Long customerId){
        return interventionRepository.getInterventionsByInquiry_CustomerIdAndConfirmedIsFalse(customerId);
    }

    public List<Intervention> getInterventionsByCustomerIdWhereConfirmedIsTrue(Long customerId){
        return interventionRepository.getInterventionsByInquiry_CustomerIdAndConfirmedIsTrue(customerId);
    }

    public List<Intervention> getPlannedInterventionsByManufacturerAndByInquiryType(Long manufacturerId, String inquiryType){
        return interventionRepository.getInterventionsByInquiry_Machine_Type_ManufacturerIdAndInquiry_InquiryTypeAndConfirmedIsFalse(manufacturerId, inquiryType);
    }

    public Boolean checkIfInquiryHasNoActiveInterventionsByInquiryId(Long inquiryId){
        return interventionRepository.countInterventionsByInquiryIdAndActiveIsTrue(inquiryId).equals(0);
    }

    public List<Intervention> getAllInterventions(){
        List<Intervention> interventions = interventionRepository.findAll();
        interventions.forEach(intervention -> {
            if(intervention.getAssistant() == null && intervention.getAssistant2() == null){
                intervention.setResourceIds(Arrays.asList(intervention.getTechnician().getId()));
            } else if (intervention.getAssistant2() == null) {
                intervention.setResourceIds(Arrays.asList(intervention.getTechnician().getId(), intervention.getAssistant().getId()));
            } else if(intervention.getAssistant() == null){
                intervention.setResourceIds(Arrays.asList(intervention.getTechnician().getId(), intervention.getAssistant2().getId()));
            } else{
                intervention.setResourceIds(Arrays.asList(intervention.getTechnician().getId(), intervention.getAssistant().getId(), intervention.getAssistant2().getId()));
            }
        });
        return interventionRepository.findAll();
    }

    public List<Intervention> getAllPlannedInterventions(){
        return interventionRepository.getAllByConfirmedIsFalse();
    }

    public List<Intervention> getAllConfirmedInterventions(){
        return interventionRepository.getAllByConfirmedIsTrue();
    }

    public Page<Intervention> findInterventionsBySearchWithPaginationAndSorting(int pageNo, int pageSize, String sortField, String sortDirection, InterventionFilter interventionFilter){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        InterventionSpecification spec1 = new InterventionSpecification(new SearchCriteria("inquiry",":",interventionFilter.getInquiryId()));
        InterventionSpecification spec2 = new InterventionSpecification(new SearchCriteria("technician",":",interventionFilter.getTechnician()));
        InterventionSpecification spec3 = new InterventionSpecification(new SearchCriteria("assistant",":",interventionFilter.getAssistant()));
        InterventionSpecification spec4 = new InterventionSpecification(new SearchCriteria("assistant2",":",interventionFilter.getAssistant2()));
        InterventionSpecification spec5 = new InterventionSpecification(new SearchCriteria("start",":",interventionFilter.getStart()));
        InterventionSpecification spec6 = new InterventionSpecification(new SearchCriteria("end",":",interventionFilter.getEnd()));
        InterventionSpecification spec7 = new InterventionSpecification(new SearchCriteria("confirmed",":",interventionFilter.getConfirmed()));

        return this.interventionRepository.findAll(Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5).and(spec6).and(spec7), pageable);
    }

    public void removeInterventionById(Long interventionId){
        
        interventionRepository.deleteById(interventionId);
    }

    public Intervention confirmInterventionById(Long interventionId){
        Intervention intervention = interventionRepository.getById(interventionId);
        intervention.setConfirmed(true);

        return interventionRepository.save(intervention);
    }
}
