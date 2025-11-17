package com.pcl.lms.dto.response;

import java.util.Date;

public class ResponseIntakeDto {
    private String id;
    private String name;
    private Date date;
    private String program;

    public ResponseIntakeDto() {
    }

    public ResponseIntakeDto(String id, String name, Date date, String program) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.program = program;
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

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
