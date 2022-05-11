package com.coderslab.crm.model;

import javax.persistence.*;

@Entity
@Table(name = "privileges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean sales = false;

    private Boolean service = false;

    private Boolean accountancy = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSales() {
        return sales;
    }

    public void setSales(Boolean sales) {
        this.sales = sales;
    }

    public Boolean getService() {
        return service;
    }

    public void setService(Boolean service) {
        this.service = service;
    }

    public Boolean getAccountancy() {
        return accountancy;
    }

    public void setAccountancy(Boolean accountancy) {
        this.accountancy = accountancy;
    }

    public Privilege(Long id, Boolean sales, Boolean service, Boolean accountancy) {
        this.id = id;
        this.sales = sales;
        this.service = service;
        this.accountancy = accountancy;
    }

    public Privilege() {
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", sales=" + sales +
                ", service=" + service +
                ", accountancy=" + accountancy +
                '}';
    }
}
