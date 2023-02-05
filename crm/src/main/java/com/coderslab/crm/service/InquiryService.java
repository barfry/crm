package com.coderslab.crm.service;

import com.coderslab.crm.model.Inquiry;
import com.coderslab.crm.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryService {

    @Autowired
    InquiryRepository inquiryRepository;
    CustomerService customerService;
    MachineService machineService;

    public InquiryService(InquiryRepository inquiryRepository, CustomerService customerService, MachineService machineService) {
        this.inquiryRepository = inquiryRepository;
        this.customerService = customerService;
        this.machineService = machineService;
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


}
