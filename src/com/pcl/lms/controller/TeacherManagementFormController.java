package com.pcl.lms.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TeacherManagementFormController {
    public AnchorPane context;
    public TextField txtTeacherId;
    public TextField txtTeacherName;
    public TextField txtContact;
    public Button btnSave;
    public TextField txtAddress;
    public TextField txtSearch;
    public TableView tblTeacher;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colOption;

    public void newTeacherOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }
}
