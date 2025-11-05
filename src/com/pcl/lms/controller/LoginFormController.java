package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.DB.DbConnection;
import com.pcl.lms.bo.BoFactory;
import com.pcl.lms.bo.custom.impl.UserBoImpl;
import com.pcl.lms.dto.response.ResponseUserDto;
import com.pcl.lms.env.StaticResource;
import com.pcl.lms.model.User;
import com.pcl.lms.utill.BoType;
import com.pcl.lms.utill.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;

public class LoginFormController {
    public AnchorPane context;
    public Label lblCompany;
    public Label lblVersion;
    public TextField txtEmail;
    public PasswordField txtPassword;
    UserBoImpl userBo= BoFactory.getInstance().getBo(BoType.USER);
    public void initialize() {
        setStaticData();
    }

    private void setStaticData() {
        lblCompany.setText(StaticResource.getCOMPANY());
        lblVersion.setText(StaticResource.getVERSION());
    }



    public void navigateDashboardOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        try{
            ResponseUserDto loginState = userBo.login(email, password);
            if (loginState != null) {
                if (loginState.getStatusCode()==200){
                    new Alert(Alert.AlertType.INFORMATION, "Login Successful!").show();
                    setUi("DashboardForm");
                }else{
                    new Alert(Alert.AlertType.ERROR, loginState.getMessage()).show();
                }

            }else {
                new Alert(Alert.AlertType.INFORMATION, "User Not Found").show();
            }


        }catch (ClassNotFoundException|SQLException e){
            e.printStackTrace();
        }




    }



    public void navigateForgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        setUi("EmailVerificationForm");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("WelcomeScreenForm");

    }
    private void setUi(String location) throws IOException {
        URL resource = getClass().getResource("/com/pcl/lms/view/"+location+".fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage= (Stage) context.getScene().getWindow();
        stage.setScene(scene);
    }
}
