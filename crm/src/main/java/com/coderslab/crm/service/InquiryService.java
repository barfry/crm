package com.coderslab.crm.service;

import com.coderslab.crm.model.Inquiry;
import com.coderslab.crm.repository.ServiceInquirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InquiryService {

    @Autowired
    ServiceInquirtRepository serviceInquirtRepository;
    CustomerService customerService;

    public InquiryService(ServiceInquirtRepository serviceInquirtRepository, CustomerService customerService) {
        this.serviceInquirtRepository = serviceInquirtRepository;
        this.customerService = customerService;
    }

    public Inquiry addNewInquiry(Inquiry inquiry, Long customerId){
        inquiry.setCustomer(customerService.getCustomerById(customerId));
        return serviceInquirtRepository.save(inquiry);
    }

    public Inquiry getInquiryById(Long inquiryId){
        return serviceInquirtRepository.getById(inquiryId);
    }

    public Inquiry editInquiry(Inquiry inquiry){
        return serviceInquirtRepository.save(inquiry);
    }
}
