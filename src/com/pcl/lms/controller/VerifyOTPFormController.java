package com.pcl.lms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class VerifyOTPFormController {
    public AnchorPane context;

    public void navigateEmailVerificationFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("EmailVerificationForm");

    }
    public void seteUserData(int verificationCode,String email){
        System.out.println(verificationCode+" "+email);
    }

    public void navigatePasswordResetFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ResetPasswordForm");
    }
    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
