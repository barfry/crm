package com.coderslab.crm.service;

import com.coderslab.crm.filter.EquipmentFilter;
import com.coderslab.crm.filter.MachineFilter;
import com.coderslab.crm.model.Equipment;
import com.coderslab.crm.model.Machine;
import com.coderslab.crm.model.User;
import com.coderslab.crm.repository.EquipmentRepository;
import com.coderslab.crm.repository.MachineRepository;
import com.coderslab.crm.specification.EquipmentSpecification;
import com.coderslab.crm.specification.MachineSpecification;
import com.coderslab.crm.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    EquipmentRepository equipmentRepository;
    MachineRepository machineRepository;
    UserService userService;

    public EquipmentService(EquipmentRepository equipmentRepository, MachineRepository machineRepository, UserService userService) {
        this.equipmentRepository = equipmentRepository;
        this.machineRepository = machineRepository;
        this.userService = userService;
    }

    public List<Equipment> getAllEquipment(){
        return equipmentRepository.findAll();
    }

    public Equipment getEquipmentById(Long equipmentId){
        return equipmentRepository.getById(equipmentId);
    }

    public List<Equipment> getEquipmentNotPresentInMachine(Long machineId){
        return equipmentRepository.getEquipmentNotPresentInMachine(machineId);
    }

    public Page<Equipment> findEquipmentBySearchWithPaginationAndSorting(int pageNo, int pageSize, String sortField, String sortDirection, EquipmentFilter equipmentFilter){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        EquipmentSpecification spec1 = new EquipmentSpecification(new SearchCriteria("code",":",equipmentFilter.getCode()));
        EquipmentSpecification spec2 = new EquipmentSpecification(new SearchCriteria("title",":",equipmentFilter.getTitle()));
        EquipmentSpecification spec3 = new EquipmentSpecification(new SearchCriteria("description",":",equipmentFilter.getDescription()));

        return this.equipmentRepository.findAll(Specification.where(spec1).and(spec2).and(spec3), pageable);
    }

    public List<Equipment> compareAndRemoveAllEquipmentWithMachineEquipment(List<Equipment> equipmentList, Long machineId){
        Machine machine = machineRepository.getById(machineId);
        for (Equipment equipment : equipmentList) {
            if (machine.getEquipmentList().contains(equipment)) {
                equipmentList.remove(equipment);
            }
        }

        return equipmentList;
    }

    public List<Equipment> getMachinesEquipment(Long machineId){
        return equipmentRepository.getEquipmentPresentInMachine(machineId);
    }

    public Equipment addNewOrEditEquipment(Equipment equipment){
        equipment.setModifier(userService.getCurrentUser());
        equipment.setUpdateDate(LocalDate.now());
        return equipmentRepository.save(equipment);
    }
}
