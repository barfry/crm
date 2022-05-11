package com.coderslab.crm.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "interventions")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "technician_id")
    private User technician;

    @OneToOne
    @JoinColumn(name = "assistant_id")
    private User assistant;

    @OneToOne
    @JoinColumn(name = "assistant2_id")
    private User assistant2;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Boolean confirmed = false;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 300, message = "This field should contain from 2 up to 300 characters")
    private String notice;

    private Boolean spareParts = false;

    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public Intervention(Long id, User technician, User assistant, User assistant2, LocalDate startDate, LocalDate endDate, Boolean confirmed, String notice, Boolean spareParts, Boolean active) {
        this.id = id;
        this.technician = technician;
        this.assistant = assistant;
        this.assistant2 = assistant2;
        this.startDate = startDate;
        this.endDate = endDate;
        this.confirmed = confirmed;
        this.notice = notice;
        this.spareParts = spareParts;
        this.active = active;
    }

    public Intervention() {
    }

    @Override
    public String toString() {
        return "Intervention{" +
                "id=" + id +
                ", technician=" + technician +
                ", assistant=" + assistant +
                ", assistant2=" + assistant2 +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", confirmed=" + confirmed +
                ", notice='" + notice + '\'' +
                ", spareParts=" + spareParts +
                ", active=" + active +
                '}';
    }
}
