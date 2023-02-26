package com.coderslab.crm.filter;

public class InterventionFilter {

    private String inquiryId = "";

    private String technician = "";

    private String assistant = "";

    private String assistant2 = "";

    private String start = "";

    private String end = "";

    private String confirmed = "";

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }


    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getAssistant2() {
        return assistant2;
    }

    public void setAssistant2(String assistant2) {
        this.assistant2 = assistant2;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public InterventionFilter(String inquiryId, String technician, String assistant, String assistant2, String start, String end, String confirmed) {
        this.inquiryId = inquiryId;
        this.technician = technician;
        this.assistant = assistant;
        this.assistant2 = assistant2;
        this.start = start;
        this.end = end;
        this.confirmed = confirmed;
    }

    public InterventionFilter() {
    }
}
