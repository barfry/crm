package com.coderslab.crm.repository;

import com.coderslab.crm.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findBySalesAndServiceAndAccountancy(Boolean sales, Boolean service, Boolean accountancy);

}
