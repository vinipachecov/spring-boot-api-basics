package com.mobileapi.mobileapi.ui.model.request;

public class UserDetailsRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
}