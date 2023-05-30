package com.coderslab.crm.repository;

import com.coderslab.crm.model.Event;
import com.coderslab.crm.model.Task;
import com.coderslab.crm.model.Type;
import com.coderslab.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.active is true AND (t.executor =?1 or t.supervisor = ?1)" +
            " order by t.plannedDate desc")
    List<Task> getActiveTasksByUser(User user);

    @Transactional
    @Modifying
    @Query("update Task t set t.name = ?1, t.description = ?2, t.plannedDate = ?3, t.executor = ?4, t.supervisor = ?5, " +
            " t.modifier = ?6 , t.updateDate = ?7  where t.id = ?8")
    void updateTask(String name, String description, LocalDate plannedDate, User executor, User supervisor,
                    User modifier, LocalDate updateDate, Long taskId);

    @Transactional
    @Modifying
    @Query("update Task t set t.updateDate = ?1, t.modifier = ?2, t.active = ?3 where t.id = ?4")
    void completeTask(LocalDate updateDate, User modifier, Boolean active, Long taskId);

}

