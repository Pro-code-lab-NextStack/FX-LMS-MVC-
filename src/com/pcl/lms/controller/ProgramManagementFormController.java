package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Programme;
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

    public void initialize() {
        setProgrammeId();
    }

    private void setProgrammeId() {
        Programme programme = Database.programmeTable.get(Database.programmeTable.size() - 1);
        if (programme != null) {
            String programmeId = programme.getProgrammeId();
            String[] splittedId = programmeId.split("-");
            String splittedLastCharacterAsString = splittedId[1];
            int lastDigit = Integer.parseInt(splittedLastCharacterAsString);
            lastDigit++;
            String genaratedID = "P-" + lastDigit;
            txtProgramId.setText(genaratedID);

        }else {
            txtProgramId.setText("P-1");
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }

    public void newProgramOnAction(ActionEvent actionEvent) {
    }
}
