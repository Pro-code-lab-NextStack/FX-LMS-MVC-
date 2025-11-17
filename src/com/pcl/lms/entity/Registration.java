package com.pcl.lms.entity;

public class Registration {
    private String studentId;
    private String programId;
    private boolean isPaid;

    public Registration() {
    }

    public Registration(String studentId, String programId, boolean isPaid) {
        this.studentId = studentId;
        this.programId = programId;
        this.isPaid = isPaid;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
