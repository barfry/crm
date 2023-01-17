package com.coderslab.crm.repository;

import com.coderslab.crm.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long>, JpaSpecificationExecutor<Machine> {

    List<Machine> getMachinesByCustomerId(Long customerId);

    List<Machine> getMachinesByType_ManufacturerId(Long manufacturerId);

    List<Machine> getMachinesByActiveTrue();
}
