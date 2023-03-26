package com.coderslab.crm.model;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 20 characters")
    private String name;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 200, message = "This field should contain from 2 up to 200 characters")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Set the planned date")
    private LocalDate plannedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate executionDate;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    @NotNull
    private User supervisor;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    @NotNull
    private User executor;

    @Length(min = 2, max = 200, message = "This field should contain between 2 and 200 characters")
    private String notice;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "modifier_id")
    private User modifier;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Task(Long id, String name, String description, LocalDate plannedDate, LocalDate executionDate, User supervisor, User executor, String notice, LocalDate updateDate, User modifier, Machine machine, Boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.plannedDate = plannedDate;
        this.executionDate = executionDate;
        this.supervisor = supervisor;
        this.executor = executor;
        this.notice = notice;
        this.updateDate = updateDate;
        this.modifier = modifier;
        this.machine = machine;
        this.active = active;
    }

    public Task() {
    }

}
