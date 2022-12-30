package com.coderslab.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", updatable = false)
    @JsonBackReference
    private Customer customer;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 20 characters")
    private String type;

    @ManyToOne
    @JoinColumn(name = "main_user_id", referencedColumnName = "id", updatable = false)
    private User mainUser;

    @ManyToOne
    @JoinColumn(name = "assisting_user_id", referencedColumnName = "id")
    private User assistingUser;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Start date must be selected")
    private LocalDateTime start;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "End date must be selected")
    private LocalDateTime end;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 80, message = "This field should contain from 2 up to 20 characters")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reminderDate;

    @Length(min = 2, max = 80, message = "This field should contain from 2 up to 20 characters")
    private String commentAfterEvent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    private LocalDate creationDate;

    @OneToOne
    @JoinColumn(name = "modifier_id")
    private User modifier;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getMainUser() {
        return mainUser;
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }

    public User getAssistingUser() {
        return assistingUser;
    }

    public void setAssistingUser(User assistingUser) {
        this.assistingUser = assistingUser;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getCommentAfterEvent() {
        return commentAfterEvent;
    }

    public void setCommentAfterEvent(String commentAfterEvent) {
        this.commentAfterEvent = commentAfterEvent;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Event(Long id, Customer customer, String type, User mainUser, User assistingUser, LocalDateTime start, LocalDateTime end, String description, LocalDate reminderDate, String commentAfterEvent, LocalDate creationDate, User modifier, LocalDate updateDate, Boolean active) {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.mainUser = mainUser;
        this.assistingUser = assistingUser;
        this.start = start;
        this.end = end;
        this.description = description;
        this.reminderDate = reminderDate;
        this.commentAfterEvent = commentAfterEvent;
        this.creationDate = creationDate;
        this.modifier = modifier;
        this.updateDate = updateDate;
        this.active = active;
    }

    public Event() {
    }

}
