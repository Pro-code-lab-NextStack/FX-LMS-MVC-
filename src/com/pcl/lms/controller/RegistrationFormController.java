package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.DB.DbConnection;
import com.pcl.lms.bo.BoFactory;
import com.pcl.lms.bo.custom.ProgrammeBo;
import com.pcl.lms.bo.custom.RegisterBo;
import com.pcl.lms.bo.custom.StudentBo;
import com.pcl.lms.dto.request.RequestRegisterDto;
import com.pcl.lms.model.Enroll;
import com.pcl.lms.model.Programme;
import com.pcl.lms.model.Student;
import com.pcl.lms.utill.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RegistrationFormController {
    public TextField txtId;
    public Button btnSave;
    public ComboBox<String> cmbProgram;
    public RadioButton rbtnPaid;
    public ToggleGroup ratePayement;
    public RadioButton rbtnUnpaid;
    public ComboBox<String> cmbStudent;
    public TextField txtSearch;
    public AnchorPane context;
    String searchText = "";
    public AnchorPane root;
    RegisterBo registerBo= BoFactory.getInstance().getBo(BoType.REGISTRATION);

    public void initialize(){
        setStudentId();
        setStudentData(searchText);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                this.searchText = newValue;
                setStudentData(searchText);
                cmbStudent.show();
            }
        });
        cmbStudent.valueProperty().addListener((observable, oldValue, newValue) -> {
            setStudentId();
        });
        setProgramData();
    }

    private void setStudentId() {
        if (cmbStudent.getValue()==null){
            txtId.setText("Select student");
        }else{
           String studentComboValue=cmbStudent.getValue();
            String[] splittedComboValue = studentComboValue.split("-");
            txtId.setText(splittedComboValue[0]+"-"+splittedComboValue[1]);
        }
    }

    private void setStudentData(String searchText) {
        try {

            ObservableList<String> studentObList = FXCollections.observableArrayList();
            List<String> studentForComboByName = registerBo.findStudentForComboByName(searchText);
            for (String student:studentForComboByName){
                studentObList.add(student);
            }
            cmbStudent.setItems(studentObList);

        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }


    }



    private void setProgramData() {
        try{
            ObservableList<String> programObList = FXCollections.observableArrayList();
            List<String> programtForCombo = registerBo.findProgramtForCombo();
            for(String program:programtForCombo){
                programObList.add(program);
            }
            cmbProgram.setItems(programObList);


        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }


    }


    public void newRegistrationOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void saveOnAction(ActionEvent actionEvent) {
        try {
            registerBo.registration(new RequestRegisterDto(
                    rbtnPaid.isSelected(),cmbProgram.getValue(),cmbStudent.getValue()
            ));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
