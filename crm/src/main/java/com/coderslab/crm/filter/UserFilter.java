package com.coderslab.crm.filter;


public class UserFilter {

    private String firstName = "";

    private String lastName = "";

    private String nickname = "";

    private String email = "";

    private String mobilePhoneNumber = "";

    private String internalPhoneNumber = "";

    private String departmentName = "";

    private String position = "";

    private String roleName = "";


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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public UserFilter(String firstName, String lastName, String nickname, String email, String mobilePhoneNumber, String internalPhoneNumber, String departmentName, String position, String roleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.internalPhoneNumber = internalPhoneNumber;
        this.departmentName = departmentName;
        this.position = position;
        this.roleName = roleName;
    }

    public UserFilter() {
    }
}
