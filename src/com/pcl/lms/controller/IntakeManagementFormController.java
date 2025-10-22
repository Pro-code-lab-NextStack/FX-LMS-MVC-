package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Intake;
import com.pcl.lms.model.Programme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class IntakeManagementFormController {
    public AnchorPane context;
    public TextField txtId;
    public Button btnSave;
    public DatePicker dteStart;
    public TextField txtName;
    public ComboBox cmbProgram;
    public TextField txtSearch;
    public TableView tblIntake;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDate;
    public TableColumn colProgram;
    public TableColumn colOption;

    public void initialize() {
        setIntakeId();
        setTeacherData();
    }

    private void setTeacherData() {
        ObservableList<String> programsObList = FXCollections.observableArrayList();
        for (Programme temp:Database.programmeTable){
            programsObList.add(temp.getProgrammeId()+"-"+temp.getProgrammeName());
        }
        cmbProgram.setItems(programsObList);

    }

    private void setIntakeId() {
        if (!Database.intakeTable.isEmpty()){
            Intake lastIntake = Database.intakeTable.get(Database.intakeTable.size() - 1);
            String id = lastIntake.getId();
            String[] split = id.split("-");
            int lastDigit = Integer.parseInt(split[1]);
            lastDigit++;
            txtId.setText("T-"+lastDigit);
        }
        txtId.setText("T-1");
    }

    public void newIntakeOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {
    }
}
