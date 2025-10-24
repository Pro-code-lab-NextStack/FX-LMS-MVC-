module lms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jbcrypt;
    requires jakarta.mail;
    requires jakarta.activation;
    requires java.sql;
    requires mysql.connector.j;

    opens com.pcl.lms.controller to javafx.fxml;
    opens com.pcl.lms.model to javafx.base;
    opens com.pcl.lms.view.tm to javafx.base;


     exports com.pcl.lms;
}
