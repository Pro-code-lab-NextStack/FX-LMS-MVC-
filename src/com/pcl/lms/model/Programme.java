package com.pcl.lms.model;

public class Programme {
    private String programmeId;
    private String programmeName;
    private double cost;
    private String teacher;
    private String [] module;

    public Programme() {
    }

    public Programme(String programmeId, String programmeName, double cost, String teacher, String[] module) {
        this.programmeId = programmeId;
        this.programmeName = programmeName;
        this.cost = cost;
        this.teacher = teacher;
        this.module = module;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String[] getModule() {
        return module;
    }

    public void setModule(String[] module) {
        this.module = module;
    }
}
