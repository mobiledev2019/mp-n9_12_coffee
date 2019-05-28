package com.example.managercoffee.MODEL;

public class staff {
    private String id;
    private String name;
    private String dob;
    private String phone;
    private String address;
    private String URL;

    public staff(String id, String name, String dob, String phone, String address, String URL) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.URL = URL;
    }

    public staff() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
