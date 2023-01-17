package com.coderslab.crm.filter;

public class MachineFilter {

    private String customerName = "";

    private String typeName = "";

    private String serialNumber = "";

    private String productionYear = "";

    private String servicePriority = "";

    private String city = "";

    private String zipCode = "";

    private String province = "";

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getServicePriority() {
        return servicePriority;
    }

    public void setServicePriority(String servicePriority) {
        this.servicePriority = servicePriority;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public MachineFilter(String customerName, String typeName, String serialNumber, String productionYear, String servicePriority, String city, String zipCode, String province) {
        this.customerName = customerName;
        this.typeName = typeName;
        this.serialNumber = serialNumber;
        this.productionYear = productionYear;
        this.servicePriority = servicePriority;
        this.city = city;
        this.zipCode = zipCode;
        this.province = province;
    }

    public MachineFilter() {
    }
}
