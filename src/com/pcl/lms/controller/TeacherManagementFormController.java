package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Teacher;
import com.pcl.lms.view.tm.TeacherTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

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

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                this.searchText=newValue;
                setTeacherData(searchText);
            }
        });
        tblTeacher.getSelectionModel().selectedItemProperty().addListener
                ((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData((TeacherTm)newValue);
            }
        });
    }

    private void setData(TeacherTm tm) {
        txtTeacherId.setText(tm.getId());
        txtTeacherName.setText(tm.getName());
        txtContact.setText(tm.getContact());
        txtAddress.setText(tm.getAddress());
        btnSave.setText("Update");
    }

    private void setTeacherData(String searchText) {
        ObservableList <TeacherTm> teacherObList = FXCollections.observableArrayList();

        for (Teacher teacher:Database.teacherTable){
            if(teacher.getName().toLowerCase().contains(searchText.toLowerCase())){
                Button btn = new Button("Delete");
                TeacherTm teacherTm = new TeacherTm(
                        teacher.getId(),
                        teacher.getName(),
                        teacher.getAddress(),
                        teacher.getContact(),
                        btn
                );
                btn.setOnAction((event) -> {
                    Alert alert=new Alert
                            (Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this teacher?"
                                    ,ButtonType.YES,ButtonType.NO);
                    alert.showAndWait();
                    if(alert.getResult()==ButtonType.YES){
                        Database.teacherTable.remove(teacher);
                        setTeacherData(searchText);
                        setTeacherId();
                        new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
                    }
                });
                teacherObList.add(teacherTm);
            }



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
        clearFields();
        btnSave.setText("Save");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void saveOnAction(ActionEvent actionEvent) {
        Teacher teacher = new Teacher(
                txtTeacherName.getText(),
                txtTeacherId.getText(),
                txtContact.getText(),
                txtAddress.getText()
        );
        if (btnSave.getText().equals("Save")) {

           Database.teacherTable.add(teacher);
           setTeacherId();
           setTeacherData(searchText);
           clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Teacher Saved").show();
        }else{
            Optional<Teacher> selectedTeacher = Database.teacherTable.stream().filter
                    (e -> e.getId().equals(teacher.getId())).findFirst();
            if(!selectedTeacher.isPresent()){
               new Alert(Alert.AlertType.INFORMATION, "Teacher Not Found").show();
               return;
            }
            selectedTeacher.get().setName(teacher.getName());
            selectedTeacher.get().setAddress(teacher.getAddress());
            selectedTeacher.get().setAddress(teacher.getAddress());
            setTeacherData(searchText);
            setTeacherId();
            clearFields();
            setTeacherId();
            btnSave.setText("Save");
            new Alert(Alert.AlertType.INFORMATION, "Teacher Updated").show();

        }
    }

    private void clearFields() {
        txtTeacherName.clear();
        txtContact.clear();
        txtAddress.clear();
    }
    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
