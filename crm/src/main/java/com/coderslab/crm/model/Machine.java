package com.coderslab.crm.model;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private String serialNumber;

    @DateTimeFormat(pattern = "yyyy")
    private LocalDate productionYear;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate commissioningDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate warrantyStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate warrantyEndDate;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 200, message = "This field should contain from 2 up to 50 characters")
    private String generalNotice;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 200, message = "This field should contain from 2 up to 50 characters")
    private String serviceNotice;

    @Min(0)
    @Max(5)
    @NotNull
    private Integer servicePriority;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @UpdateTimestamp
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}")
    private String zipCode;

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

    private Boolean active = true;

    @OneToMany
    @JoinTable(name = "machine_inquiries", joinColumns =
        @JoinColumn(name = "machine_id"), inverseJoinColumns =
            @JoinColumn(name = "inquiry_id"))
    private Set<Inquiry> inquiryList;

    @OneToMany
    @JoinTable(name = "machine_tasks", joinColumns =
        @JoinColumn(name = "machine_id"), inverseJoinColumns =
            @JoinColumn(name = "task_id"))
    private Set<Task> taskList;


    @OneToMany
    @JoinTable(name = "machine_equipment", joinColumns =
        @JoinColumn(name = "machine_id"), inverseJoinColumns =
            @JoinColumn(name = "equipment_id"))
    private Set<Equipment> equipmentList;

    @OneToMany
    @JoinTable(name = "machine_categories", joinColumns =
        @JoinColumn(name = "machine_id"), inverseJoinColumns =
            @JoinColumn(name = "category_id"))
    private Set<Category> categoryList;

    @OneToOne
    @JoinColumn(name = "modifier_id")
    private User modifier;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(LocalDate productionYear) {
        this.productionYear = productionYear;
    }

    public LocalDate getCommissioningDate() {
        return commissioningDate;
    }

    public void setCommissioningDate(LocalDate commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    public LocalDate getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public void setWarrantyStartDate(LocalDate warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    public LocalDate getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(LocalDate warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public String getGeneralNotice() {
        return generalNotice;
    }

    public void setGeneralNotice(String generalNotice) {
        this.generalNotice = generalNotice;
    }

    public String getServiceNotice() {
        return serviceNotice;
    }

    public void setServiceNotice(String serviceNotice) {
        this.serviceNotice = serviceNotice;
    }

    public Integer getServicePriority() {
        return servicePriority;
    }

    public void setServicePriority(Integer servicePriority) {
        this.servicePriority = servicePriority;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Inquiry> getServiceInquiryList() {
        return inquiryList;
    }

    public void setServiceInquiryList(Set<Inquiry> inquiryList) {
        this.inquiryList = inquiryList;
    }

    public Set<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(Set<Task> taskList) {
        this.taskList = taskList;
    }

    public Set<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(Set<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public Set<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(Set<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Machine(Long id, Customer customer, Type type, Manufacturer manufacturer, String serialNumber, LocalDate productionYear, LocalDate commissioningDate, LocalDate warrantyStartDate, LocalDate warrantyEndDate, String generalNotice, String serviceNotice, Integer servicePriority, LocalDate creationDate, User creator, Contract contract, String zipCode, String city, String street, Integer streetNumber, Boolean active, Set<Inquiry> inquiryList, Set<Task> taskList, Set<Equipment> equipmentList, Set<Category> categoryList, User modifier, Date updateDate) {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.manufacturer = manufacturer;
        this.serialNumber = serialNumber;
        this.productionYear = productionYear;
        this.commissioningDate = commissioningDate;
        this.warrantyStartDate = warrantyStartDate;
        this.warrantyEndDate = warrantyEndDate;
        this.generalNotice = generalNotice;
        this.serviceNotice = serviceNotice;
        this.servicePriority = servicePriority;
        this.creationDate = creationDate;
        this.creator = creator;
        this.contract = contract;
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.active = active;
        this.inquiryList = inquiryList;
        this.taskList = taskList;
        this.equipmentList = equipmentList;
        this.categoryList = categoryList;
        this.modifier = modifier;
        this.updateDate = updateDate;
    }

    public Machine() {
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", customer=" + customer +
                ", type=" + type +
                ", manufacturer=" + manufacturer +
                ", serialNumber='" + serialNumber + '\'' +
                ", productionYear=" + productionYear +
                ", commissioningDate=" + commissioningDate +
                ", warrantyStartDate=" + warrantyStartDate +
                ", warrantyEndDate=" + warrantyEndDate +
                ", generalNotice='" + generalNotice + '\'' +
                ", serviceNotice='" + serviceNotice + '\'' +
                ", servicePriority=" + servicePriority +
                ", creationDate=" + creationDate +
                ", creator=" + creator +
                ", contract=" + contract +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", active=" + active +
                ", serviceInquiryList=" + inquiryList +
                ", taskList=" + taskList +
                ", equipmentList=" + equipmentList +
                ", categoryList=" + categoryList +
                ", modifier=" + modifier +
                ", updateDate=" + updateDate +
                '}';
    }
}
