package com.pcl.lms.dto.request;

public class RequestProgrameDto {
    private String id;
    private String name;
    private double cost;
    private String teacher;
    private String [] module;

    public RequestProgrameDto() {
    }

    public RequestProgrameDto(String id, String name, double cost, String teacher, String[] module) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.teacher = teacher;
        this.module = module;
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

    public String[] getModule() {
        return module;
    }

    public void setModule(String[] module) {
        this.module = module;
    }
}
