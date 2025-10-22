package com.pcl.lms.model;

public class Modules {
    private int Id;
    private String Name;

    public Modules() {
    }

    public Modules(String name, int id) {
        Name = name;
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
