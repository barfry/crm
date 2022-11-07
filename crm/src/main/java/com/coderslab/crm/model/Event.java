package com.coderslab.crm.model;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 20 characters")
    private String type;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_responsible_users", joinColumns =
        @JoinColumn(name = "user_id"), inverseJoinColumns =
            @JoinColumn(name = "event_id"))
    private Set<User> responsibleUsers;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    private LocalTime eventTime;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 20 characters")
    private String title;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 80, message = "This field should contain from 2 up to 20 characters")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reminderDate;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 80, message = "This field should contain from 2 up to 20 characters")
    private String commentAfterEvent;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public Set<User> getResponsibleUsers() {
        return responsibleUsers;
    }

    public void setResponsibleUsers(Set<User> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public Event(Long id, Customer customer, String type, Set<User> responsibleUsers, LocalDate eventDate, LocalTime eventTime, String title, String description, LocalDate reminderDate, String commentAfterEvent, User creator, LocalDate creationDate, User modifier, LocalDate updateDate, Boolean active) {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.responsibleUsers = responsibleUsers;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.title = title;
        this.description = description;
        this.reminderDate = reminderDate;
        this.commentAfterEvent = commentAfterEvent;
        this.creator = creator;
        this.creationDate = creationDate;
        this.modifier = modifier;
        this.updateDate = updateDate;
        this.active = active;
    }

    public Event() {
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", customer=" + customer +
                ", type='" + type + '\'' +
                ", responsibleUsers=" + responsibleUsers +
                ", eventDate=" + eventDate +
                ", eventTime=" + eventTime +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", reminderDate=" + reminderDate +
                ", commentAfterEvent='" + commentAfterEvent + '\'' +
                ", creator=" + creator +
                ", creationDate=" + creationDate +
                ", modifier=" + modifier +
                ", updateDate=" + updateDate +
                ", active=" + active +
                '}';
    }
}
