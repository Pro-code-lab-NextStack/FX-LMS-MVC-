package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.DB.DbConnection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
    String userEmail;

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
        try {
            ArrayList<Student> studentList= fetchStudentData(searchText);
            ObservableList<StudentTm> studentTm= FXCollections.observableArrayList();

            for (Student st:studentList){


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
                         try{
                             boolean isDelete=deleteStudent(st);
                             new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
                             setTableData(searchText);
                             setStudentId();
                         }catch (SQLException|ClassNotFoundException e){}

                        }


                    });
                    studentTm.add(tm);


            }
            tblStudent.setItems(studentTm);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }


    }

    private boolean deleteStudent(Student st) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM student WHERE id=?");
        ps.setString(1, st.getStudentId());
       return ps.executeUpdate()>0;
    }

    private ArrayList<Student> fetchStudentData(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<Student> studentList = new ArrayList<>();
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDENT WHERE name LIKE ?");
        ps.setString(1,"%"+searchText+"%");
        ResultSet set = ps.executeQuery();

        while(set.next()){
            studentList.add(new Student
                    (set.getString(1),set.getString(2),set.getString(3),set.getDate(4)));
        }
        return studentList;
    }


    public void saveOnAction(ActionEvent actionEvent) {
        Student student = new Student(
                txtStudentId.getText(),
                txtStudentName.getText(),
                txtAddress.getText(),
                Date.from(dteDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
        );
        try {
            if (btnSave.getText().equals("Save")) {

                boolean isSaved = saveStudent(student, userEmail);
                if (isSaved) {
                    setStudentId();
                    clearFields();
                    new Alert(Alert.AlertType.INFORMATION,"Student Saved").show();
                    setTableData(searchText);
                }


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
        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }


    }

    private boolean saveStudent(Student student, String userEmail) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO student VALUES(?,?,?,?,?)");
        ps.setString(1,student.getStudentId());
        ps.setString(2,student.getStudentName());
        ps.setString(3,student.getStudentAddress());
        ps.setObject(4,student.getDob());
        ps.setString(5,userEmail);
        return ps.executeUpdate()>0;
    }

    private void setStudentId() {

        try{
            String lastStudentId= getLastStudent();

            if (lastStudentId!=null){
                String[] splittedId = lastStudentId.split("-");
                String lastCharAsString = splittedId[1];
                int lastDigit= Integer.parseInt(lastCharAsString);
                lastDigit++;
                String genaratedId = "S-" + lastDigit;
                txtStudentId.setText(genaratedId);
            }else {
                txtStudentId.setText("S-1");
            }



        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    private String getLastStudent() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM student ORDER BY CAST(SUBSTRING(id,3)AS UNSIGNED)DESC LIMIT 1");
        ResultSet set = statement.executeQuery();
        if (set.next()) {
            return set.getString(1);
        }
        return  null;

    }

    private void clearFields(){
        txtStudentName.clear();
        txtAddress.clear();
        dteDob.setValue(null);
        setTableData(searchText);
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
    public void setUserEmail(String userEmail) {
        this.userEmail=userEmail;
        System.out.println(this.userEmail);
    }
}
