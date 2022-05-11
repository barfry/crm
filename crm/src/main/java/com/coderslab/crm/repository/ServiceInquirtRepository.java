package com.coderslab.crm.repository;

import com.coderslab.crm.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceInquirtRepository extends JpaRepository<Inquiry, Long> {
}
