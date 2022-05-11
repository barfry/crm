package com.coderslab.crm.model;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name = "contact_persons")
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 20 characters")
    private String firstName;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 20 characters")
    private String lastName;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 20 characters")
    private String position;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Pattern(regexp = "^\\d{9}$")
    private String stationaryPhoneNumber;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Pattern(regexp = "^\\d{9}$")
    private String mobilePhoneNumber;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Email
    private String email;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 2, max = 20, message = "This field should contain from 2 up to 20 characters")
    private String profile;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User modifier;

    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStationaryPhoneNumber() {
        return stationaryPhoneNumber;
    }

    public void setStationaryPhoneNumber(String stationaryPhoneNumber) {
        this.stationaryPhoneNumber = stationaryPhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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

    public ContactPerson(Long id, String firstName, String lastName, String position, String stationaryPhoneNumber, String mobilePhoneNumber, String email, String profile, Date updateDate, User modifier, Boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.stationaryPhoneNumber = stationaryPhoneNumber;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.email = email;
        this.profile = profile;
        this.updateDate = updateDate;
        this.modifier = modifier;
        this.active = active;
    }

    public ContactPerson() {
    }

    @Override
    public String toString() {
        return "ContactPerson{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", stationaryPhoneNumber='" + stationaryPhoneNumber + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", profile='" + profile + '\'' +
                ", updateDate=" + updateDate +
                ", modifier=" + modifier +
                ", active=" + active +
                '}';
    }
}
