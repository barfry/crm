package com.coderslab.crm.model;

import com.coderslab.crm.validation.OnPersist;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

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
    @Length(message = "This field should contain 3 characters")
    private String nickname;

    //Add duplicate error validation
    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Email(message = "Please enter proper e-mail format")
    @Column(unique = true)
    private String email;

    @NotNull(groups = OnPersist.class)
    @NotBlank(message = "This field can't be empty", groups = OnPersist.class)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,128}$", message="The password must contain from 6 up to 128 characters, at least 1 lowercase letter, 1 capital letter and 1 digit", groups = OnPersist.class)
    private String password;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private String mobilePhoneNumber;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private String internalPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "department_id",  referencedColumnName = "id")
    @NotNull(message = "New user must be assigned to any department")
    private Department department;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private String position;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns =
        @JoinColumn(name = "user_id"), inverseJoinColumns =
            @JoinColumn(name = "role_id"))
    @OrderBy("name ASC")
    private Set<Role> roles;

    private Boolean enabled = true;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getInternalPhoneNumber() {
        return internalPhoneNumber;
    }

    public void setInternalPhoneNumber(String internalPhoneNumber) {
        this.internalPhoneNumber = internalPhoneNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public User(Long id, String firstName, String lastName, String nickname, String email, String password, String mobilePhoneNumber, String internalPhoneNumber, Department department, String position, Set<Role> roles, Boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.internalPhoneNumber = internalPhoneNumber;
        this.department = department;
        this.position = position;
        this.roles = roles;
        this.enabled = enabled;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", internalPhoneNumber='" + internalPhoneNumber + '\'' +
                ", department=" + department +
                ", position='" + position + '\'' +
                ", roles=" + roles +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
