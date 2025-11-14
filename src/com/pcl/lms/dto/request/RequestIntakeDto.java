package com.pcl.lms.dto.request;

import java.util.Date;

public class RequestIntakeDto {
    private String intakeId;
    private String intakeName;
    private Date date;
    private String program;

    public RequestIntakeDto() {
    }

    public RequestIntakeDto(String intakeId, String intakeName, Date date, String program) {
        this.intakeId = intakeId;
        this.intakeName = intakeName;
        this.date = date;
        this.program = program;
    }

    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
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
