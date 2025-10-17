package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Student;
import com.pcl.lms.view.tm.StudentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class StudentManagementFormController {
    public AnchorPane context;
    public TextField txtStudentId;
    public TextField txtStudentName;
    public TextField txtAddress;
    public DatePicker dteDob;
    public TextField txtSearch;
    public Button btnSave;
    public TableView <StudentTm> tblStudent;
    public TableColumn<StudentTm,String> colID;
    public TableColumn<StudentTm,String> colName;
    public TableColumn<StudentTm,String> colAddress;
    public TableColumn<StudentTm,Date> colDob;
    public TableColumn<StudentTm,Button> colOption;
    String searchText="";

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setStudentId();
        setTableData(searchText);

        tblStudent.getSelectionModel().selectedItemProperty().addListener
                ((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        setData((StudentTm)newValue);
                    }

        });
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                this.searchText = newValue;
                setTableData(newValue);
        });

    }

    private void setData(StudentTm newValue) {
        txtStudentId.setText(newValue.getId());
        txtStudentName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());

        dteDob.setValue(LocalDate.parse(newValue.getDob()));
        btnSave.setText("Update");

    }

    private void setTableData(String newValue) {
        ObservableList<StudentTm> studentTm= FXCollections.observableArrayList();
        for (Student st:Database.studentTable){
            if (st.getStudentName().contains(newValue)){
                Button btn=new Button("Delete");
                StudentTm tm=new StudentTm(
                        st.getStudentId(),
                        st.getStudentName(),
                        st.getStudentAddress(),
                        new SimpleDateFormat("yyyy-MM-dd").format(st.getDob()),
                        btn
                );
                btn.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this student "
                            , ButtonType.YES, ButtonType.NO);
                    alert.showAndWait();

                    if (alert.getResult()==ButtonType.YES){
                        Database.studentTable.remove(st);
                        new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
                        setTableData(searchText);
                        setStudentId();
                    }


                });
                studentTm.add(tm);
            }

        }
        tblStudent.setItems(studentTm);
    }



    public void saveOnAction(ActionEvent actionEvent) {
        Student student = new Student(
                txtStudentId.getText(),
                txtStudentName.getText(),
                txtAddress.getText(),
                Date.from(dteDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
        );
        if (btnSave.getText().equals("Save")) {

            Database.studentTable.add(student);
            System.out.println(student.toString());
            setStudentId();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION,"Student Saved").show();
            setTableData(searchText);
        }else{
            Optional<Student> selectedStudent =
                    Database.studentTable.stream().filter(e -> e.getStudentId().equals(txtStudentId.getText()))
                            .findFirst();
            if (selectedStudent.isPresent()) {
                selectedStudent.get().setStudentName(txtStudentName.getText());
                selectedStudent.get().setStudentAddress(txtAddress.getText());
                selectedStudent.get().setDob(Date.from(dteDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                new Alert(Alert.AlertType.INFORMATION,"Student Updated").show();
                setStudentId();
                clearFields();
                setTableData(searchText);
                btnSave.setText("Save");
            }

        }

    }

    private void setStudentId() {
        if(!Database.studentTable.isEmpty()){
            Student lastStudent = Database.studentTable.get(Database.studentTable.size() - 1);
            String lastStudentId = lastStudent.getStudentId();
            String[] splitData = lastStudentId.split("-");
            String lastCharacter = splitData[1];
            int lastDigit=Integer.parseInt(lastCharacter);
            lastDigit++;
            String genaratedId = "S-" + lastDigit;
            txtStudentId.setText(genaratedId);


        }else {
            txtStudentId.setText("S-1");
        }
    }
    private void clearFields(){
        txtStudentName.clear();
        txtAddress.clear();
        dteDob.setValue(null);
    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }
    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
