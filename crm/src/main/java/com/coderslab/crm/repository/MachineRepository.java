package com.coderslab.crm.repository;

import com.coderslab.crm.model.Machine;
import com.coderslab.crm.model.Type;
import com.coderslab.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long>, JpaSpecificationExecutor<Machine> {

    List<Machine> getMachinesByCustomerId(Long customerId);

    List<Machine> getMachinesByType_ManufacturerId(Long manufacturerId);

    List<Machine> getMachinesByActiveTrue();

    @Transactional
    @Modifying
    @Query("update Machine m set m.type = ?1, m.serialNumber = ?2, m.productionYear = ?3, m.generalNotice = ?4, m.serviceNotice = ?5, m.servicePriority = ?6, m.province = ?7, m.zipCode = ?8, m.city = ?9, m.street = ?10, m.streetNumber = ?11 " +
            "where m.id = ?12")
    void updateMachine(Type type, String serialNumber, String productionYear, String generalNotice, String serviceNotice, Integer servicePriority, String province, String zipCode, String city, String street, Integer streetNumber, Long machineId);

}
