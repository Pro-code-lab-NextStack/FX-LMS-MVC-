package com.pcl.lms.entity;

public class Enroll {
    private String student;
    private String program;
    private Boolean isPaid;

    public Enroll() {
    }

    public Enroll(String student, String program, Boolean isPaid) {
        this.student = student;
        this.program = program;
        this.isPaid = isPaid;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
