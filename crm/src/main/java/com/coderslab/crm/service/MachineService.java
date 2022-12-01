package com.coderslab.crm.service;

import com.coderslab.crm.model.Customer;
import com.coderslab.crm.model.Machine;
import com.coderslab.crm.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
