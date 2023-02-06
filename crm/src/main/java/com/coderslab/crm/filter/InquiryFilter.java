package com.coderslab.crm.filter;

public class InquiryFilter {

    private String customerName = "";

    private String machineType = "";

    private String machineStatus = "";

    private String inquiryType = "";

    private String inquiryDate = "";

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getMachineStatus() {
        return machineStatus;
    }

    public void setMachineStatus(String machineStatus) {
        this.machineStatus = machineStatus;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(String inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public InquiryFilter() {
    }

    public InquiryFilter(String customerName, String machineType, String machineStatus, String inquiryType, String inquiryDate) {
        this.customerName = customerName;
        this.machineType = machineType;
        this.machineStatus = machineStatus;
        this.inquiryType = inquiryType;
        this.inquiryDate = inquiryDate;
    }
}
