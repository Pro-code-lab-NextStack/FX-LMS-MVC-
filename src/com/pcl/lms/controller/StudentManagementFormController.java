package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.DB.DbConnection;
import com.pcl.lms.bo.BoFactory;
import com.pcl.lms.bo.custom.impl.StudentBoImpl;
import com.pcl.lms.dto.request.RequestStudentDto;
import com.pcl.lms.dto.response.ResponseStudentDto;
import com.pcl.lms.env.Session;
import com.pcl.lms.model.Student;
import com.pcl.lms.utill.BoType;
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
import java.util.List;
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
    StudentBoImpl studentBo= BoFactory.getInstance().getBo(BoType.STUDENT);
    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setStudentId();
        setTableData(searchText);
        System.out.println(Session.getEmail());

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
                List<ResponseStudentDto> students = studentBo.getStudents(newValue);
                ObservableList<StudentTm> studentTmObservableList = FXCollections.observableArrayList();
                for (ResponseStudentDto st:students){
                    Button btnDelete=new Button("Delete");
                    studentTmObservableList.add(new StudentTm(
                            st.getId(),
                            st.getName(),
                            st.getAddress(),
                          st.getDob(),
                          btnDelete
                    ));
                    btnDelete.setOnAction((event) -> {
                       Alert alert= new Alert(Alert.AlertType.CONFIRMATION, "Are you sure", ButtonType.YES,ButtonType.NO);
                        alert.showAndWait();
                        if (alert.getResult()==ButtonType.YES){
                            try {
                                boolean isDelted = deleteStudent(st.getId());
                                if (isDelted){
                                    new Alert(Alert.AlertType.INFORMATION, "Student deleted").show();
                                    setTableData(searchText);
                                    setStudentId();

                                }
                            } catch (SQLException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            tblStudent.setItems(studentTmObservableList);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private boolean deleteStudent(String st) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM student WHERE id=?");
        ps.setString(1, st);
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

        try {
            if (btnSave.getText().equals("Save")) {

                boolean isSaved = studentBo.saveStudent(new RequestStudentDto(
                        txtStudentId.getText(),
                        txtStudentName.getText(),
                        txtAddress.getText(),
                        Date.from(dteDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())

                ));


                if (isSaved) {
                    setStudentId();
                    clearFields();
                    new Alert(Alert.AlertType.INFORMATION,"Student Saved").show();
                    setTableData(searchText);
                }


            }else{
                boolean isUpdated = studentBo.updateStudent(new RequestStudentDto(
                        txtStudentId.getText(),
                        txtStudentName.getText(),
                        txtAddress.getText(),
                        Date.from(dteDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
                ));

                if (isUpdated) {

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

    private boolean updateStudent(Student student, String userEmail) throws SQLException, ClassNotFoundException {
        Connection connection=DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE student SET name=?,address=?,dob=?,user_email=? WHERE id=?");
        ps.setString(1,student.getStudentName());
        ps.setString(2,student.getStudentAddress());
        ps.setObject(3,student.getDob());
        ps.setObject(4,userEmail);
        ps.setString(5,student.getStudentId());
        return ps.executeUpdate()>0;
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
