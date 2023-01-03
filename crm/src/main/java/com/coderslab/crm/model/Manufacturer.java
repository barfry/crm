package com.coderslab.crm.model;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "manufacturers")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 30, message = "This field should contain from 2 up to 30 characters")
    private String name;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 30 characters")
    private String city;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 30 characters")
    private String street;

    @NotNull(message = "This field can't be empty")
    @Min(1)
    @Max(1000)
    private Integer streetNumber;

    @NotNull
    @NotBlank(message = "This field can't be empty")
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
    @Pattern(regexp = "^\\d{10}$")
    private String taxCode;

    @OneToMany
    private Set<ContactPerson> contactPersonList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "manufacturer_category", joinColumns =
        @JoinColumn(name = "manufacturer_id"), inverseJoinColumns =
            @JoinColumn(name = "category_id"))
    private Set<Category> categoryList;


    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private List<Type> types;

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

    public Set<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(Set<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
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

    public Manufacturer(Long id, String name, String city, String street, Integer streetNumber, String zipCode, String phoneNumber, String email, String webPage, String taxCode, Set<ContactPerson> contactPersonList, Set<Category> categoryList, List<Type> types, Date updateDate, User modifier, Boolean active) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.webPage = webPage;
        this.taxCode = taxCode;
        this.contactPersonList = contactPersonList;
        this.categoryList = categoryList;
        this.types = types;
        this.updateDate = updateDate;
        this.modifier = modifier;
        this.active = active;
    }

    public Manufacturer() {
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", zipCode='" + zipCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", webPage='" + webPage + '\'' +
                ", taxCode='" + taxCode + '\'' +
                ", contactPersonList=" + contactPersonList +
                ", categoryList=" + categoryList +
                ", updateDate=" + updateDate +
                ", modifier=" + modifier +
                ", active=" + active +
                '}';
    }
}
