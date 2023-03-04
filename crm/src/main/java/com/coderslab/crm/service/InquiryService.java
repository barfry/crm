package com.coderslab.crm.service;

import com.coderslab.crm.filter.InquiryFilter;
import com.coderslab.crm.filter.UserFilter;
import com.coderslab.crm.model.Inquiry;
import com.coderslab.crm.model.Intervention;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.InquiryRepository;
import com.coderslab.crm.specification.InquirySpecification;
import com.coderslab.crm.specification.SearchCriteria;
import com.coderslab.crm.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryService {

    @Autowired
    InquiryRepository inquiryRepository;
    CustomerService customerService;
    MachineService machineService;
    InterventionService interventionService;

    public InquiryService(InquiryRepository inquiryRepository, CustomerService customerService, MachineService machineService, InterventionService interventionService) {
        this.inquiryRepository = inquiryRepository;
        this.customerService = customerService;
        this.machineService = machineService;
        this.interventionService = interventionService;
    }

    public Inquiry addNewInquiry(Inquiry inquiry, Long customerId){
        inquiry.setCustomer(customerService.getCustomerById(customerId));
        return inquiryRepository.save(inquiry);
    }

    public Inquiry getInquiryById(Long inquiryId){
        return inquiryRepository.getById(inquiryId);
    }

    public Inquiry editInquiry(Inquiry inquiry){
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getInquiriesByMachineId(Long machineId){
        return inquiryRepository.getInquiriesByMachineId(machineId);
    }

    public Integer countInquiriesByCustomerId(Long customerId){
        return inquiryRepository.countInquiriesByCustomerId(customerId);
    }

    public Inquiry addNewInquiryToMachine(Inquiry inquiry, Long machineId){
        inquiry.setMachine(machineService.getMachineById(machineId));
        inquiry.setCustomer(inquiry.getMachine().getCustomer());
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getAllInquiries(){
        return inquiryRepository.findAll();
    }

    public Page<Inquiry> findInquiriesBySearchWithPaginationAndSorting(int pageNo, int pageSize, String sortField, String sortDirection, InquiryFilter inquiryFilter){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        InquirySpecification spec1 = new InquirySpecification(new SearchCriteria("customer",":",inquiryFilter.getCustomerName()));
        InquirySpecification spec2 = new InquirySpecification(new SearchCriteria("machine",":",inquiryFilter.getMachineType()));
        InquirySpecification spec3 = new InquirySpecification(new SearchCriteria("machineStatus",":",inquiryFilter.getMachineStatus()));
        InquirySpecification spec4 = new InquirySpecification(new SearchCriteria("inquiryType",":",inquiryFilter.getInquiryType()));
        InquirySpecification spec5 = new InquirySpecification(new SearchCriteria("inquiryDate",":",inquiryFilter.getInquiryDate()));

        return this.inquiryRepository.findAll(Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5), pageable);
    }

    public Inquiry disableInquiry(Long inquiryId){
        Inquiry inquiry = inquiryRepository.getById(inquiryId);
        inquiry.setActive(false);
        return inquiryRepository.save(inquiry);
    }

    public Boolean checkIfInquiryHasNoActiveInterventionsByInquiryId(Long inquiryId){
        return interventionService.checkIfInquiryHasNoActiveInterventionsByInquiryId(inquiryId);
    }


}
