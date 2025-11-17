package com.pcl.lms.entity;

import java.sql.Date;

public class Intake {
    private String id;
    private String name;
    private Date date;
    private String programId;

    public Intake() {
    }

    public Intake(String id, String name, Date date, String programId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.programId = programId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }
}
