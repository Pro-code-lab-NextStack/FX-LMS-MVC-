package com.pcl.lms.controller;

import com.pcl.lms.env.StaticResource;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardFormController {
    public Label lblDate;
    public Label lblTime;
    public Label lblCompany;
    public Label lblVersion;
    public AnchorPane context;

    public void initialize(){
        setData();
    }

    private void setData() {
        lblCompany.setText(StaticResource.getCOMPANY());
        lblVersion.setText(StaticResource.getVERSION());

        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        lblDate.setText(dateFormat);


        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            String timeFormat=new SimpleDateFormat("HH:mm:ss").format(new Date());
            lblTime.setText(timeFormat);
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();


    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    public void studentManageOnAction(ActionEvent actionEvent) throws IOException {
        setUi("StudentManagementForm");

    }
    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
