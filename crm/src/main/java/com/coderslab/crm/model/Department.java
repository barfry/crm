package com.coderslab.crm.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private String name;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    private Privilege privilege;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public Department(Long id, String name, String description, Privilege privilege) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.privilege = privilege;
    }

    public Department() {
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", privilegeList=" + privilege +
                '}';
    }
}
