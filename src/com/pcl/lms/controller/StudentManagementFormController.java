package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Student;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class StudentManagementFormController {
    public AnchorPane context;
    public TextField txtStudentId;
    public TextField txtStudentName;
    public TextField txtAddress;
    public DatePicker dteDob;
    public TextField txtSearch;
    public Button btnSave;

    public void initialize(){
        setStudentId();
    }

    private void setStudentId() {
        if(!Database.studentTable.isEmpty()){
            Student lastStudent = Database.studentTable.get(Database.studentTable.size() - 1);
            String lastStudentId = lastStudent.getStudentId();
            String[] splitData = lastStudentId.split("-");
            String lastCharacter = splitData[1];
            int lastDigit=Integer.parseInt(lastCharacter);
            lastDigit++;
            String genaratedId = "S-" + lastDigit;
            txtStudentId.setText(genaratedId);


        }else {
            txtStudentId.setText("S-1");
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {

    }
}
