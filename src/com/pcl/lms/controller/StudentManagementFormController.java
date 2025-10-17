package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Student;
import com.pcl.lms.view.tm.StudentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.text.SimpleDateFormat;
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
    public TableView <StudentTm> tblStudent;
    public TableColumn<StudentTm,String> colID;
    public TableColumn<StudentTm,String> colName;
    public TableColumn<StudentTm,String> colAddress;
    public TableColumn<StudentTm,Date> colDob;
    public TableColumn<StudentTm,Button> colOption;

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setStudentId();
        setTableData();

        tblStudent.getSelectionModel().selectedItemProperty().addListener
                ((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        setData((StudentTm)newValue);
                    }

        });
    }

    private void setData(StudentTm newValue) {
        txtStudentId.setText(newValue.getId());
        txtStudentName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());

        //dteDob.setValue(newValue.getDob());
    }

    private void setTableData() {
        ObservableList<StudentTm> studentTm= FXCollections.observableArrayList();
        for (Student st:Database.studentTable){
            Button btn=new Button("Delete");
            StudentTm tm=new StudentTm(
                    st.getStudentId(),
                    st.getStudentName(),
                    st.getStudentAddress(),
                    new SimpleDateFormat("yyyy-MM-dd").format(st.getDob()),
                    btn
            );
            btn.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this student "
                        , ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if (alert.getResult()==ButtonType.YES){
                    Database.studentTable.remove(st);
                    new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
                    setTableData();
                    setStudentId();
                }


            });
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
        setTableData();
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
