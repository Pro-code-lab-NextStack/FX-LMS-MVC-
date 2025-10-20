package com.pcl.lms.view.tm;

import javafx.scene.control.Button;

public class TeacherTm {
    private String id;
    private String name;
    private String address;
    private String contact;
    private Button btn;

    @Override
    public String toString() {
        return "TeacherTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", btn=" + btn +
                '}';
    }

    public TeacherTm() {
    }

    public TeacherTm(String id, String name, String address, String contact, Button btn) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
