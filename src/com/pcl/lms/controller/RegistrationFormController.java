package com.pcl.lms.controller;

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
    public ComboBox cmbProgram;
    public RadioButton ratePaid;
    public ToggleGroup ratePayement;
    public RadioButton rbtnUnpaid;
    public ComboBox cmbStudent;
    public TextField txtSearch;
    public AnchorPane context;

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
