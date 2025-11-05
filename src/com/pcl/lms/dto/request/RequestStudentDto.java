package com.pcl.lms.dto.request;

import java.util.Date;

public class RequestStudentDto {
    private String id;
    private String name;
    private String address;
    private Date dob;

    public RequestStudentDto() {
    }

    public RequestStudentDto(String id, String name, String address, Date dob) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dob = dob;
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
}
