package com.pcl.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPasswordFromController {
    public AnchorPane context;

    public void navigateOtpVerificationForm(ActionEvent actionEvent) throws IOException {
        setUi("VerifyOTPForm");
    }

    public void resetPasswordOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }


    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
