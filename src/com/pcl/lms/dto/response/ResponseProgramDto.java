package com.pcl.lms.dto.response;

public class ResponseProgramDto {
    private String id;
    private String name;
    private double cost;
    private String teacher;

    public ResponseProgramDto() {
    }

    public ResponseProgramDto(String id, String name, double cost, String teacher) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.teacher = teacher;
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
}
