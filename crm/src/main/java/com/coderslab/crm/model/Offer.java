package com.coderslab.crm.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @OneToMany
    @JoinTable(name = "offer_equipment", joinColumns =
        @JoinColumn(name = "offer_id"),inverseJoinColumns =
            @JoinColumn(name = "equipment_id"))
    private Set<Equipment> equipmentList;

    @NotBlank
    @NotNull
    private Double netValue;

    private Boolean active = true;

    @NotNull
    @NotBlank(message = "This field can't be empty")
    @Length(min = 20, max = 200, message = "This field should contain 200 characters")
    private String notice;

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

    public Set<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(Set<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public Double getNetValue() {
        return netValue;
    }

    public void setNetValue(Double netValue) {
        this.netValue = netValue;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Offer(Long id, Customer customer, Type type, Set<Equipment> equipmentList, Double netValue, Boolean active, String notice) {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.equipmentList = equipmentList;
        this.netValue = netValue;
        this.active = active;
        this.notice = notice;
    }

    public Offer() {
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", customer=" + customer +
                ", type=" + type +
                ", equipmentList=" + equipmentList +
                ", netValue=" + netValue +
                ", active=" + active +
                ", notice='" + notice + '\'' +
                '}';
    }
}
