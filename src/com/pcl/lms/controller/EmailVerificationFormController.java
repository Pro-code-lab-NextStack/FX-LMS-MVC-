package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.User;
import com.pcl.lms.utill.tools.VerificationCodeGenarator;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.io.IOException;

import java.util.Optional;
import java.util.Properties;

public class EmailVerificationFormController {
    public AnchorPane context;
    public TextField txtEmail;
    private int generatedOtp;


    public void navigateVerifyCodeOnAction(ActionEvent actionEvent) throws IOException {

        // otp genarate
    // email send
    //navigate with parse data(email,otp)
        try {
            final String fromEmail = "septembergihan@gmail.com";
            final String password = "qvpm kaph lxdg mvpo"; // no spaces
            final String toEmail = txtEmail.getText();

            Optional<User> selectedUser = Database.userTable.stream()
                    .filter(e -> e.getEmail().equals(toEmail))
                    .findFirst();

            if (selectedUser.isPresent()) {



                int otp = new VerificationCodeGenarator().getCode(4);
                System.out.println("Generated OTP: " + otp);
                this.generatedOtp = otp;

                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject("Email Verification Code");
                message.setText("Your OTP is: " + otp);

                Transport.send(message);
                System.out.println("OTP sent successfully to " + toEmail);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pcl/lms/view/VerifyOTPForm.fxml"));
                Parent parent=loader.load();
                VerifyOTPFormController controller = loader.getController();
                controller.seteUserData(generatedOtp,toEmail);

               Stage stage= (Stage) context.getScene().getWindow();
               stage.setScene(new Scene(parent));

            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }



    }
    private void setUi(String location) throws IOException {

        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }


}
