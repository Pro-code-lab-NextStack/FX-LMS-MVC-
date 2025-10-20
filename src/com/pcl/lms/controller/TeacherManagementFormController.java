package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Teacher;
import com.pcl.lms.view.tm.TeacherTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class TeacherManagementFormController {
    public AnchorPane context;
    public TextField txtTeacherId;
    public TextField txtTeacherName;
    public TextField txtContact;
    public Button btnSave;
    public TextField txtAddress;
    public TextField txtSearch;
    public TableView <TeacherTm>tblTeacher;
    public TableColumn<TeacherTm,String> colId;
    public TableColumn<TeacherTm,String> colName;
    public TableColumn <TeacherTm,String>colContact;
    public TableColumn<TeacherTm,String> colAddress;
    public TableColumn<TeacherTm,Button> colOption;
    String searchText="";

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setTeacherId();
        setTeacherData(searchText);
    }

    private void setTeacherData(String searchText) {
        ObservableList <TeacherTm> teacherObList = FXCollections.observableArrayList();

        for (Teacher teacher:Database.teacherTable){
            Button btn = new Button("Delete");
            TeacherTm teacherTm = new TeacherTm(
                    teacher.getId(),
                    teacher.getName(),
                    teacher.getAddress(),
                    teacher.getContact(),
                    btn


            );
            teacherObList.add(teacherTm);

        }
        tblTeacher.setItems(teacherObList);
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
           setTeacherData(searchText);
            new Alert(Alert.AlertType.INFORMATION, "Teacher Saved").show();
        }else{
           // update
        }
    }
}
