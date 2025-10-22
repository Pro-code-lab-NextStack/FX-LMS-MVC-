package com.pcl.lms.view.tm;

import javafx.scene.control.Button;

public class ProgrammeTm {
    private String programmeId;
    private String programmeName;
    private String teacher;
    private Button btnModules;
    private double cost;
    private Button btnDelete;

    public ProgrammeTm() {
    }

    public ProgrammeTm(String programmeId, String programmeName, String teacher, Button btnModules, double cost, Button btnDelete) {
        this.programmeId = programmeId;
        this.programmeName = programmeName;
        this.teacher = teacher;
        this.btnModules = btnModules;
        this.cost = cost;
        this.btnDelete = btnDelete;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Button getBtnModules() {
        return btnModules;
    }

    public void setBtnModules(Button btnModules) {
        this.btnModules = btnModules;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }
}
