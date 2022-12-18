package com.coderslab.crm.repository;

import com.coderslab.crm.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> getInquiriesByMachineId(Long machineId);

    Integer countInquiriesByCustomerId(Long customerId);


}
