package com.coderslab.crm.filter;

import org.springframework.stereotype.Service;

@Service
public class DepartmentFilter {

    private String name = "";

    private String description = "";

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

    public DepartmentFilter() {
    }

    public DepartmentFilter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "DepartmentFilter{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
