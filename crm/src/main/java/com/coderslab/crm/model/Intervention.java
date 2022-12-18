package com.coderslab.crm.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "interventions")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "You must select an inquiry")
    private Inquiry inquiry;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "technician_id")
    private User technician;

    @ManyToOne
    @JoinColumn(name = "assistant_id", nullable = true)
    private User assistant;

    @ManyToOne
    @JoinColumn(name = "assistant2_id")
    private User assistant2;

    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    @NotNull(message = "Start date must be selected")
    private LocalDateTime start;

    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    @NotNull(message = "End date must be selected")
    private LocalDateTime end;

    private Boolean confirmed = false;

    private String notice;

    private Boolean spareParts = false;

    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }

    public User getTechnician() {
        return technician;
    }

    public void setTechnician(User technician) {
        this.technician = technician;
    }

    public User getAssistant() {
        return assistant;
    }

    public void setAssistant(User assistant) {
        this.assistant = assistant;
    }

    public User getAssistant2() {
        return assistant2;
    }

    public void setAssistant2(User assistant2) {
        this.assistant2 = assistant2;
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

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Boolean getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(Boolean spareParts) {
        this.spareParts = spareParts;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Intervention(Long id, User technician, User assistant, User assistant2, LocalDateTime start, LocalDateTime end, Boolean confirmed, String notice, Boolean spareParts, Boolean active) {
        this.id = id;
        this.technician = technician;
        this.assistant = assistant;
        this.assistant2 = assistant2;
        this.start = start;
        this.end = end;
        this.confirmed = confirmed;
        this.notice = notice;
        this.spareParts = spareParts;
        this.active = active;
    }

    public Intervention() {
    }

}
