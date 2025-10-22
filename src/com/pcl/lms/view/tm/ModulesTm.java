package com.pcl.lms.view.tm;

import javafx.scene.control.Button;

public class ModulesTm {
    private int id;
    private String Name;
    private Button btn;

    public ModulesTm() {
    }

    public ModulesTm(int id, String name, Button btn) {
        this.id = id;
        Name = name;
        this.btn = btn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
