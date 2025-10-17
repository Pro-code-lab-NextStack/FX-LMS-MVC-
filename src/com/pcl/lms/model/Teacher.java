package com.pcl.lms.model;

public class Teacher {
    private String id;
    private String name;
    private String contact;
    private String address;

    public Teacher() {
    }

    public Teacher(String name, String id, String contact, String address) {
        this.name = name;
        this.id = id;
        this.contact = contact;
        this.address = address;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
