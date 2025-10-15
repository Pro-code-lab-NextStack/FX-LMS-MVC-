package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.User;
import com.pcl.lms.utill.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ResetPasswordFromController {
    public AnchorPane context;
    public Label lblCompany;
    public Label lblVersion;
    public PasswordField txtNewPassword;
    public PasswordField txtConfirmPassword;
    private  String email;

    public void SetUserData(String email){
            this.email=email;
    }

    public void navigateOtpVerificationForm(ActionEvent actionEvent) throws IOException {
        setUi("VerifyOTPForm");
    }

    public void resetPasswordOnAction(ActionEvent actionEvent) throws IOException {
        Optional<User> selectedUser = Database.userTable.stream().filter(u -> u.getEmail().equals(email)).findFirst();

        if(selectedUser.isPresent()){
            if (txtNewPassword.getText().trim().equals(txtConfirmPassword.getText().trim())){
                selectedUser.get().setPassword(new PasswordManager().encode(txtNewPassword.getText().trim()));
                new Alert(Alert.AlertType.INFORMATION,"Password Reset Successful").show();
                System.out.println(selectedUser.get().getPassword());
                setUi("LoginForm");

            }else {
                new Alert(Alert.AlertType.ERROR,"Password doesn't mach").show();
            }
        }
    }


    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
