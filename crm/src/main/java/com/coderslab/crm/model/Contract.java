package com.coderslab.crm.model;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Date;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 20, max = 20, message = "This field should contain 20 characters")
    private String contractNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate contractDate;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 20, max = 20, message = "This field should contain 20 characters")
    private String offerNumber;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private Double netValue;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private String currency;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 20 characters")
    private String externalOrderNumber;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private User supervisor;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 200, message = "This field should contain from 2 up to 200 characters")
    private String generalNotice;

    @OneToOne(mappedBy = "contract")
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "modifier_id")
    private User modifier;

    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDate getContractDate() {
        return contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public String getOfferNumber() {
        return offerNumber;
    }

    public void setOfferNumber(String offerNumber) {
        this.offerNumber = offerNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getNetValue() {
        return netValue;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExternalOrderNumber() {
        return externalOrderNumber;
    }

    public void setExternalOrderNumber(String externalOrderNumber) {
        this.externalOrderNumber = externalOrderNumber;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public String getGeneralNotice() {
        return generalNotice;
    }

    public void setGeneralNotice(String generalNotice) {
        this.generalNotice = generalNotice;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Contract(Long id, String contractNumber, LocalDate contractDate, String offerNumber, Customer customer, Manufacturer manufacturer, Double netValue, String currency, String externalOrderNumber, User seller, User supervisor, String generalNotice, Machine machine, Payment payment, Date updateDate, User modifier, Boolean active) {
        this.id = id;
        this.contractNumber = contractNumber;
        this.contractDate = contractDate;
        this.offerNumber = offerNumber;
        this.customer = customer;
        this.manufacturer = manufacturer;
        this.netValue = netValue;
        this.currency = currency;
        this.externalOrderNumber = externalOrderNumber;
        this.seller = seller;
        this.supervisor = supervisor;
        this.generalNotice = generalNotice;
        this.machine = machine;
        this.payment = payment;
        this.updateDate = updateDate;
        this.modifier = modifier;
        this.active = active;
    }

    public Contract() {
    }

}
