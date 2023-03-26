package com.coderslab.crm.repository;

import com.coderslab.crm.model.Event;
import com.coderslab.crm.model.Task;
import com.coderslab.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.active is true AND (t.executor =?1 or t.supervisor = ?1) order by t.plannedDate desc")
    List<Task> getActiveTasksByUser(User user);
}

