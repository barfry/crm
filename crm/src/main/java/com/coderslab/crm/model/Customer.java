package com.coderslab.crm.model;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 80, message = "This field should contain from 2 up to 80 characters")
    private String name;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 30, message = "This field should contain from 2 up to 30 characters")
    private String city;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 30, message = "This field should contain from 2 up to 30 characters")
    private String street;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Min(1)
    @Max(1000)
    private Integer streetNumber;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}")
    private String zipCode;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Pattern(regexp = "^\\d{9}$")
    private String phoneNumber;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Email
    private String email;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 50, message = "This field should contain from 2 up to 50 characters")
    private String webPage;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private String province;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Pattern(regexp = "^\\d{10}$")
    private String taxCode;

    @OneToMany
    private Set<ContactPerson> contactPersonList;

    @OneToMany(mappedBy = "customer")
    private Set<Event> eventList;

    @OneToMany(mappedBy = "customer")
    private Set<Machine> machineList;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Min(1)
    @Max(5)
    private Integer servicePriority;

    @OneToMany
    @JoinTable(name = "customer_contracts", joinColumns =
        @JoinColumn(name = "customer_id"), inverseJoinColumns =
            @JoinColumn(name = "contract_id"))
    private Set<Contract> contractList;

    @OneToMany
    @JoinTable(name = "customer_offers",joinColumns =
        @JoinColumn(name = "customer_id"), inverseJoinColumns =
            @JoinColumn(name = "offer_id"))
    private Set<Offer> offerList;

    @OneToMany
    @JoinTable(name = "customer_category", joinColumns =
        @JoinColumn(name = "customer_id"), inverseJoinColumns =
            @JoinColumn(name = "category_id"))
    private Set<Category> categoryList;

    @OneToMany
    @JoinTable(name = "customer_payments", joinColumns =
        @JoinColumn(name = "customer_id"), inverseJoinColumns =
            @JoinColumn(name = "payment_id"))
    private Set<Payment> paymentList;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

    @OneToOne
    @JoinColumn(name = "modifier_id")
    private User modifier;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Set<ContactPerson> getContactPersonList() {
        return contactPersonList;
    }

    public void setContactPersonList(Set<ContactPerson> contactPersonList) {
        this.contactPersonList = contactPersonList;
    }

    public Set<Event> getEventList() {
        return eventList;
    }

    public void setEventList(Set<Event> eventList) {
        this.eventList = eventList;
    }

    public Set<Machine> getMachineList() {
        return machineList;
    }

    public void setMachineList(Set<Machine> machineList) {
        this.machineList = machineList;
    }

    public Integer getServicePriority() {
        return servicePriority;
    }

    public void setServicePriority(Integer servicePriority) {
        this.servicePriority = servicePriority;
    }

    public Set<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(Set<Contract> contractList) {
        this.contractList = contractList;
    }

    public Set<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(Set<Offer> offerList) {
        this.offerList = offerList;
    }

    public Set<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(Set<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Set<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(Set<Payment> paymentList) {
        this.paymentList = paymentList;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Customer(Long id, String name, String city, String street, Integer streetNumber, String zipCode, String phoneNumber, String email, String webPage, String province, String taxCode, Set<ContactPerson> contactPersonList, Set<Event> eventList, Set<Machine> machineList, Integer servicePriority, Set<Contract> contractList, Set<Offer> offerList, Set<Category> categoryList, Set<Payment> paymentList, LocalDate updateDate, User modifier, Boolean active) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.webPage = webPage;
        this.province = province;
        this.taxCode = taxCode;
        this.contactPersonList = contactPersonList;
        this.eventList = eventList;
        this.machineList = machineList;
        this.servicePriority = servicePriority;
        this.contractList = contractList;
        this.offerList = offerList;
        this.categoryList = categoryList;
        this.paymentList = paymentList;
        this.updateDate = updateDate;
        this.modifier = modifier;
        this.active = active;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", zipCode='" + zipCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", webPage='" + webPage + '\'' +
                ", province='" + province + '\'' +
                ", taxCode='" + taxCode + '\'' +
                ", contactPersonList=" + contactPersonList +
                ", eventList=" + eventList +
                ", machineList=" + machineList +
                ", servicePriority=" + servicePriority +
                ", contractList=" + contractList +
                ", offerList=" + offerList +
                ", categoryList=" + categoryList +
                ", paymentList=" + paymentList +
                ", updateDate=" + updateDate +
                ", modifier=" + modifier +
                ", active=" + active +
                '}';
    }
}
