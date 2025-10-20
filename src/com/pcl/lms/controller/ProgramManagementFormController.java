package com.pcl.lms.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class ProgramManagementFormController {
    public TextField txtProgramId;
    public TextField txtProgramName;
    public TextField txtCost;
    public ComboBox cbxTeacher;
    public TextField txtModules;
    public TableView tblModule;
    public TableColumn colModuleId;
    public TableColumn colModuleName;
    public TableColumn colModuleRemove;
    public Button btnSave;
    public TableView tblProgramme;
    public TableColumn colProgrammeId;
    public TableColumn colProgrammeName;
    public TableColumn colTeacher;
    public TableColumn colModuleList;
    public TableColumn colCost;
    public TableColumn colOption;
    public TextField txtSearch;

    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }

    public void newProgramOnAction(ActionEvent actionEvent) {
    }
}
