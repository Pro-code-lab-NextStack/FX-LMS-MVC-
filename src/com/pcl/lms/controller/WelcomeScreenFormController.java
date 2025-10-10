package com.pcl.lms.controller;

import com.pcl.lms.env.StaticResource;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class WelcomeScreenFormController {
    public AnchorPane context;
    public Label lblCompany;
    public Label lblVersion;

    public void initialize() {
        setStaticData();
    }

    private void setStaticData() {
        lblCompany.setText(StaticResource.getCOMPANY());
        lblVersion.setText(StaticResource.getVERSION());
    }

    public void navigateLoginFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/com/pcl/lms/view/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage=(Stage) context.getScene().getWindow();
        stage.setScene(scene);

    }

    public void navigateSignupFormOnAction(ActionEvent actionEvent) {
    }
}
