package com.example.covid_19;

public class UserHelperClass {
    String name, username, email, phoneNo, password;
    public String imageURL;
    public UserHelperClass() {
    }
    public UserHelperClass(String name, String email, String password, String phoneNo) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public UserHelperClass(String name, String email, String password, String phoneNo, String imageURL) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.imageURL=imageURL;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getImageURL() {
        return imageURL;
    }
}
