package com.coderslab.crm.repository;

import com.coderslab.crm.model.Department;
import com.coderslab.crm.model.Event;
import com.coderslab.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public List<Event> getEventByCustomerId(Long customerId);

    @Transactional
    @Modifying
    @Query("update Event e set e.type = ?1, e.assistingUser = ?2, e.start = ?3, e.end = ?4, e.description = ?5 " +
            "where e.id = ?6")
    void updateEvent(String type, User assistingUser, LocalDateTime start, LocalDateTime end, String description, Long id);

    @Transactional
    @Modifying
    @Query("update Event e set e.commentAfterEvent = ?1 " +
            "where e.id = ?2")
    void addCommentToEvent(String commentAfterEvent, Long eventId);

    List<Event> getEventsByCustomerIdAndActiveIsTrue(Long customerId);

    List<Event> getEventsByCustomerIdAndActiveIsFalse(Long customerId);

}
