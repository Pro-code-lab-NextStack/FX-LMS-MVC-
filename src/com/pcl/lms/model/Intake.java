package com.pcl.lms.model;

import java.util.Date;

public class Intake {
    private String id;
    private Date date;
    private String name;
    private String programme;

    public Intake() {
    }

    public Intake(String id, Date date, String name, String programme) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.programme = programme;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }
}
