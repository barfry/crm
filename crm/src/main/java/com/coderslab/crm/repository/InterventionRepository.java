package com.coderslab.crm.repository;

import com.coderslab.crm.model.Intervention;
import com.coderslab.crm.model.Manufacturer;
import com.coderslab.crm.model.User;
import org.hibernate.event.service.spi.JpaBootstrapSensitive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long>, JpaSpecificationExecutor<Intervention> {

    @Transactional
    @Modifying
    @Query("update Intervention i set i.technician = ?1, i.assistant = ?2, i.assistant2 = ?3, i.start = ?4, i.end = ?5, i.spareParts = ?6 , i.notice = ?7 where" +
            " i.id = ?8")
    void updateIntervention(User technician, User assistant, User assistant2, LocalDateTime start, LocalDateTime end, Boolean spareParts, String notice, Long interventionId);

    Integer countInterventionsByInquiry_CustomerId(Long customerId);

    public List<Intervention> getInterventionsByInquiry_CustomerId(Long customerId);

    List<Intervention> getInterventionsByInquiry_CustomerIdAndConfirmedIsFalse(Long customerId);
    List<Intervention> getInterventionsByInquiry_CustomerIdAndConfirmedIsTrue(Long customerId);

    public List<Intervention> getInterventionsByInquiry_InquiryTypeAndInquiry_CustomerId(String inquiryType, Long customerId);

    List<Intervention> getInterventionsByInquiry_Machine_Type_ManufacturerIdAndInquiry_InquiryTypeAndConfirmedIsFalse(Long manufacturerId, String inquiryType);

    Integer countInterventionsByInquiryIdAndActiveIsTrue(Long inquiryId);

    List<Intervention> getAllByConfirmedIsTrue();

    List<Intervention> getAllByConfirmedIsFalse();
}
