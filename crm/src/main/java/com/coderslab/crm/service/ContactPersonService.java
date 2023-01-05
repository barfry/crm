package com.coderslab.crm.service;

import com.coderslab.crm.model.ContactPerson;
import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.Manufacturer;
import com.coderslab.crm.repository.ContactPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContactPersonService {

    @Autowired
    ContactPersonRepository contactPersonRepository;
    UserService userService;
    CustomerService customerService;
    ManufacturerService manufacturerService;

    public ContactPersonService(ContactPersonRepository contactPersonRepository, UserService userService, CustomerService customerService, ManufacturerService manufacturerService) {
        this.contactPersonRepository = contactPersonRepository;
        this.userService = userService;
        this.customerService = customerService;
        this.manufacturerService = manufacturerService;
    }

    public ContactPerson getContactPersonById(Long id){
        return  contactPersonRepository.getById(id);
    }

    public void addNewContactPersonToCustomer(ContactPerson contactPerson, Customer customer){
        contactPerson.setModifier(userService.getCurrentUser());
        contactPerson.setUpdateDate(new Date());

        contactPersonRepository.save(contactPerson);

       customerService.addNewContactPersonToCustomer(contactPerson, customer);
    }

    public void editContactPerson(ContactPerson contactPerson){
        contactPerson.setModifier(userService.getCurrentUser());
        contactPerson.setUpdateDate(new Date());

        contactPersonRepository.save(contactPerson);
    }

    public void disableContactPerson(Long contactId){
        ContactPerson contactPerson = contactPersonRepository.getById(contactId);

        contactPerson.setActive(false);
        contactPerson.setModifier(userService.getCurrentUser());
        contactPerson.setUpdateDate(new Date());

        contactPersonRepository.save(contactPerson);
    }

    public void activateContactPerson(Long contactId){
        ContactPerson contactPerson = contactPersonRepository.getById(contactId);

        contactPerson.setActive(true);
        contactPerson.setModifier(userService.getCurrentUser());
        contactPerson.setUpdateDate(new Date());

        contactPersonRepository.save(contactPerson);
    }

    public void addNewContactPersonToManufacturer(ContactPerson contactPerson, Manufacturer manufacturer){
        contactPerson.setModifier(userService.getCurrentUser());
        contactPerson.setUpdateDate(new Date());

        contactPersonRepository.save(contactPerson);

        manufacturerService.addNewContactPersonToManufacturer(contactPerson, manufacturer);
    }
}
