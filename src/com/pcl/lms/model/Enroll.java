package com.pcl.lms.model;

public class Enroll {
    private String student;
    private String programme;
    private boolean isPaid;

    public Enroll() {
    }

    public Enroll(String student, String programme, boolean isPaid) {
        this.student = student;
        this.programme = programme;
        this.isPaid = isPaid;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
