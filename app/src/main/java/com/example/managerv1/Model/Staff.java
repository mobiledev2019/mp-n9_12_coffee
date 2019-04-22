package com.example.managerv1.Model;

import java.util.Date;

public class Staff {
    private String name;
    private Date dob;
    private String address;
    private String phone;

    public Staff(String name, Date dob, String address, String phone) {
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
    }

    public Staff() {
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
