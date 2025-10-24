package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Programme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationFormController {
    public TextField txtId;
    public Button btnSave;
    public ComboBox<String> cmbProgram;
    public RadioButton ratePaid;
    public ToggleGroup ratePayement;
    public RadioButton rbtnUnpaid;
    public ComboBox cmbStudent;
    public TextField txtSearch;
    public AnchorPane context;

    public void initialize(){
        setProgramData();
    }

    private void setProgramData() {
        ObservableList<String> programObList = FXCollections.observableArrayList();
        programObList.clear();


        if (!Database.programmeTable.isEmpty()) {
            for (Programme programme:Database.programmeTable){
                programObList.add(programme.getProgrammeId()+"-"+programme.getProgrammeName());
            }
            cmbProgram.setItems(programObList);
        }else {
            cmbProgram.setValue("Programms not found");
        }

    }

    public void newRegistrationOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void saveOnAction(ActionEvent actionEvent) {

    }
    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
