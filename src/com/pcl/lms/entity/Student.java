package com.pcl.lms.entity;

import java.sql.Date;

public class Student {
    private String id;
    private String name;
    private String address;
    private Date dob;
    private String user_email;

    public Student() {
    }

    public Student(String id, String name, String address, Date dob, String user_email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.user_email = user_email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
