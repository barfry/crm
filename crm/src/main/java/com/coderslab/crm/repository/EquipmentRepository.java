package com.coderslab.crm.repository;

import com.coderslab.crm.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long>, JpaSpecificationExecutor<Equipment> {


    @Query(value = "SELECT * FROM equipment WHERE Id NOT IN (SELECT Equipment_id FROM machine_equipment WHERE Machine_id = ?1)", nativeQuery = true)
    List<Equipment> getEquipmentNotPresentInMachine(Long machineId);

    @Query(value = "SELECT * FROM equipment WHERE Id IN (SELECT Equipment_id FROM machine_equipment WHERE Machine_id = ?1)", nativeQuery = true)
    List<Equipment> getEquipmentPresentInMachine(Long machineId);
}
