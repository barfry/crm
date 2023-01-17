package com.coderslab.crm.service;

import com.coderslab.crm.filter.MachineFilter;
import com.coderslab.crm.filter.ManufacturerFilter;
import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.Machine;
import com.coderslab.crm.model.Manufacturer;
import com.coderslab.crm.repository.MachineRepository;
import com.coderslab.crm.specification.MachineSpecification;
import com.coderslab.crm.specification.ManufacturerSpecification;
import com.coderslab.crm.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class MachineService {

    @Autowired
    MachineRepository machineRepository;
    UserService userService;

    public MachineService(MachineRepository machineRepository, UserService userService) {
        this.machineRepository = machineRepository;
        this.userService = userService;
    }

    public Machine addNewMachine(Machine machine, Customer customer){
        machine.setCreator(userService.getCurrentUser());
        machine.setCreationDate(LocalDate.now());

        machine.setModifier(userService.getCurrentUser());
        machine.setUpdateDate(new Date());
        machine.setCustomer(customer);

        return machineRepository.save(machine);
    }

    public Machine getMachineById(Long machineId){
        return machineRepository.getById(machineId);
    }

    public Machine editMachine(Machine machine){
        machine.setModifier(userService.getCurrentUser());
        machine.setUpdateDate(new Date());

        return machineRepository.save(machine);
    }

    public Machine disableMachine(Long machineId){
        Machine machine = machineRepository.getById(machineId);
        machine.setActive(false);
        machine.setUpdateDate(new Date());
        machine.setModifier(userService.getCurrentUser());
        return machineRepository.save(machine);
    }

    public Machine activateMachine(Long machineId){
        Machine machine = machineRepository.getById(machineId);
        machine.setActive(true);
        machine.setUpdateDate(new Date());
        machine.setModifier(userService.getCurrentUser());
        return machineRepository.save(machine);
    }

    public List<Machine> getMachinesByCustomerId(Long customerId){
        return machineRepository.getMachinesByCustomerId(customerId);
    }

    public List<Machine> getMachinesByManufacturerId(Long manufacturerId){
        return machineRepository.getMachinesByType_ManufacturerId(manufacturerId);
    }

    public List<Machine> getAllActiveMachines(){
        return machineRepository.getMachinesByActiveTrue();
    }

    public List<Machine> getAllMachines(){
        return machineRepository.findAll();
    }

    public Page<Machine> findMachinesBySearchWithPaginationAndSorting(int pageNo, int pageSize, String sortField, String sortDirection, MachineFilter machineFilter){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        MachineSpecification spec1 = new MachineSpecification(new SearchCriteria("customer",":",machineFilter.getCustomerName()));
        MachineSpecification spec2 = new MachineSpecification(new SearchCriteria("type",":",machineFilter.getTypeName()));
        MachineSpecification spec3 = new MachineSpecification(new SearchCriteria("serialNumber",":",machineFilter.getSerialNumber()));
        MachineSpecification spec4 = new MachineSpecification(new SearchCriteria("productionYear",":",machineFilter.getProductionYear()));
        MachineSpecification spec5 = new MachineSpecification(new SearchCriteria("servicePriority",":",machineFilter.getServicePriority()));
        MachineSpecification spec6 = new MachineSpecification(new SearchCriteria("city",":",machineFilter.getCity()));
        MachineSpecification spec7 = new MachineSpecification(new SearchCriteria("zipCode",":",machineFilter.getZipCode()));
        MachineSpecification spec8 = new MachineSpecification(new SearchCriteria("province",":",machineFilter.getProvince()));

        return this.machineRepository.findAll(Specification.where(spec1).and(spec2).and(spec3).and(spec4).and(spec5).and(spec6).and(spec7).and(spec8), pageable);
    }
}
