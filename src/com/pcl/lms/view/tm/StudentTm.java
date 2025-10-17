package com.pcl.lms.view.tm;

import javafx.scene.control.Button;

import java.util.Date;

public class StudentTm {
    private String id;
    private String name;
    private String address;
    private String dob;
    private Button btn;

    public StudentTm() {
    }

    public StudentTm(String id, String name, String address, String dob, Button btn) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.btn = btn;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
