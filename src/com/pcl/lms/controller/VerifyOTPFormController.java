package com.pcl.lms.controller;

import com.pcl.lms.env.StaticResource;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class VerifyOTPFormController {
    public AnchorPane context;
    public Label lblUserEmail;
    public TextField txtOtp;
    public Label lblCompany;
    public Label lblVersion;
    private int OTP;
    private String email;

    public void initialize() {
        setLabelData();
    }

    private void setLabelData() {
        lblCompany.setText(StaticResource.getCOMPANY());
        lblVersion.setText(StaticResource.getVERSION());
        lblUserEmail.setText(email);
    }

    public void navigateEmailVerificationFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("EmailVerificationForm");

    }
    public void seteUserData(int verificationCode,String email){
        this.email=email;
        this.OTP=verificationCode;

    }

    public void navigatePasswordResetFormOnAction(ActionEvent actionEvent) throws IOException {
        if(OTP==Integer.parseInt(txtOtp.getText())){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/pcl/lms/view/ResetPasswordForm.fxml"));
            Parent load = fxmlLoader.load();
            ResetPasswordFromController controller = fxmlLoader.getController();
            controller.SetUserData(email);
            Stage stage =(Stage) context.getScene().getWindow();
            stage.setScene(new Scene(load));


        }else{
          new Alert(Alert.AlertType.ERROR,"Invalid OTP").show();
        }
    }
    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
