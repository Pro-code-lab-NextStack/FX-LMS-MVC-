package com.pcl.lms.controller;

import com.pcl.lms.env.StaticResource;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class LoginFormController {
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



    public void navigateDashboardOnAction(ActionEvent actionEvent) {

    }

    public void navigateForgotPasswordOnAction(ActionEvent actionEvent) {
    }
}
