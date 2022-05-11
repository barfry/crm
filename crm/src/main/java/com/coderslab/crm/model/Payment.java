package com.coderslab.crm.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name =  "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 10, max = 10, message = "This field should contain 10 characters")
    private String code;

    @NotNull
    @NotBlank
    private Double netValue;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate invoiceDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getNetValue() {
        return netValue;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Payment() {
    }

    public Payment(Long id, String code, Double netValue, LocalDate invoiceDate, Date dueDate, Boolean active) {
        this.id = id;
        this.code = code;
        this.netValue = netValue;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", netValue=" + netValue +
                ", invoiceDate=" + invoiceDate +
                ", dueDate=" + dueDate +
                ", active=" + active +
                '}';
    }
}
