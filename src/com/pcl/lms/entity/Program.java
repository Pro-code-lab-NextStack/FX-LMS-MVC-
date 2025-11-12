package com.pcl.lms.entity;

public class Program {
    private String id;
    private String name;
    private double cost;
    private String teacherId;


    public Program() {
    }

    public Program(String id, String name, double cost, String teacherId) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.teacherId = teacherId;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
