package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Student;
import com.pcl.lms.view.tm.StudentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.time.ZoneId;
import java.util.Date;

public class StudentManagementFormController {
    public AnchorPane context;
    public TextField txtStudentId;
    public TextField txtStudentName;
    public TextField txtAddress;
    public DatePicker dteDob;
    public TextField txtSearch;
    public Button btnSave;
    public TableView tblStudent;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colDob;
    public TableColumn colOption;

    public void initialize(){
        setStudentId();
        setTableData();
    }

    private void setTableData() {
        ObservableList<StudentTm> studentTm= FXCollections.observableArrayList();
        for (Student st:Database.studentTable){
            Button btn=new Button("Delete");
            StudentTm tm=new StudentTm(
                    st.getStudentId(),
                    st.getStudentName(),
                    st.getStudentAddress(),
                    st.getDob(),
                    btn
            );
            studentTm.add(tm);
        }
        tblStudent.setItems(studentTm);
    }


    public void saveOnAction(ActionEvent actionEvent) {
        Student student = new Student(
                txtStudentId.getText(),
                txtStudentName.getText(),
                txtAddress.getText(),
                Date.from(dteDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
        );
        Database.studentTable.add(student);
        System.out.println(student.toString());
        setStudentId();
        clearFields();
        new Alert(Alert.AlertType.INFORMATION,"Student Saved").show();
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
    private void clearFields(){
        txtStudentName.clear();
        txtAddress.clear();
        dteDob.setValue(null);
    }
}
