package com.coderslab.crm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "inquiries")
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private String machineStatus;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private String inquiryType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inquiryDate;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 300, message = "This field should contain from 2 up to 300 characters")
    private String description;

    private Boolean active = true;

    private String comment;

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Intervention> interventionList;

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

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public String getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public LocalDate getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(LocalDate inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<Intervention> getInterventionList() {
        return interventionList;
    }

    public void setInterventionList(Set<Intervention> interventionList) {
        this.interventionList = interventionList;
    }

    public Inquiry(Long id, Customer customer, Machine machine, String machineStatus, String inquiryType, LocalDate inquiryDate, String description, Boolean active, String comment, Set<Intervention> interventionList) {
        this.id = id;
        this.customer = customer;
        this.machine = machine;
        this.machineStatus = machineStatus;
        this.inquiryType = inquiryType;
        this.inquiryDate = inquiryDate;
        this.description = description;
        this.active = active;
        this.comment = comment;
        this.interventionList = interventionList;
    }

    public Inquiry() {
    }

}
