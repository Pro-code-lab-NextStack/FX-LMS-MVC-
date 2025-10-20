package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Teacher;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
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

    public void initialize(){
        setTeacherId();
    }

    private void setTeacherId() {
        if (!Database.teacherTable.isEmpty()) {
            Teacher lastTeacher = Database.teacherTable.get(Database.teacherTable.size() - 1);
            String[] splittedTeacherId = lastTeacher.getId().split("-");
            String lastCharacterAsString = splittedTeacherId[1];
            int lastDigit = Integer.parseInt(lastCharacterAsString);
            lastDigit++;
            String genaratedId="T-"+lastDigit;
            txtTeacherId.setText(genaratedId);
        }else {
            txtTeacherId.setText("T-1");
        }
    }

    public void newTeacherOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if (btnSave.getText().equals("Save")) {
           Teacher teacher = new Teacher(
                   txtTeacherName.getText(),
                   txtTeacherId.getText(),
                   txtContact.getText(),
                   txtAddress.getText()
           );
           Database.teacherTable.add(teacher);
           setTeacherId();
            new Alert(Alert.AlertType.INFORMATION, "Teacher Saved").show();
        }else{
           // update
        }
    }
}
