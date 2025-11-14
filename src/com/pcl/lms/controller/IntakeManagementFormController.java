package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.DB.DbConnection;
import com.pcl.lms.bo.BoFactory;
import com.pcl.lms.bo.custom.IntakeBo;
import com.pcl.lms.dto.request.RequestIntakeDto;
import com.pcl.lms.model.Intake;
import com.pcl.lms.model.Programme;
import com.pcl.lms.utill.BoType;
import com.pcl.lms.view.tm.IntakeTm;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class IntakeManagementFormController {
    public AnchorPane context;
    public TextField txtId;
    public Button btnSave;
    public DatePicker dteStart;
    public TextField txtName;
    public ComboBox<String> cmbProgram;
    public TextField txtSearch;
    public TableView <IntakeTm>tblIntake;
    public TableColumn<IntakeTm,String> colId;
    public TableColumn<IntakeTm,String> colName;
    public TableColumn<IntakeTm,Date> colDate;
    public TableColumn<IntakeTm,String> colProgram;
    public TableColumn<IntakeTm,Button> colOption;
    private String searchText="";

    IntakeBo intakeBo= BoFactory.getInstance().getBo(BoType.INTAKE);
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programme"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setIntakeId();
        setProgrammeData();
        loadTableData(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
           this. searchText = newValue;
           loadTableData(searchText);
        });
        tblIntake.getSelectionModel().selectedItemProperty().addListener
                ((observable, oldValue, newValue) -> {
                    if(newValue!=null) {
                        setDataToForm((IntakeTm)newValue);

                    }
                });
    }

    private void setDataToForm(IntakeTm tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getName());
        dteStart.setValue(LocalDate.parse(tm.getDate().toString()));
        cmbProgram.setValue(tm.getProgramme());
        btnSave.setText("Update");
    }

    private void loadTableData(String searchText) {
        try {
            ObservableList<Intake> intakeFromMysql= fetchIntakeData(searchText);

            ObservableList<IntakeTm> intakeObList = FXCollections.observableArrayList();
            intakeObList.clear();
            for (Intake intake:intakeFromMysql){

                    Button btn=new Button("Delete");
                    intakeObList.add(new IntakeTm(
                            intake.getId(),
                            intake.getDate(),
                            intake.getName(),
                            intake.getProgramme(),
                            btn
                    ));
                    btn.setOnAction((event) -> {
                        Alert delAlert=  new Alert(Alert.AlertType.CONFIRMATION, "Are you sure", ButtonType.YES,ButtonType.NO);
                        delAlert.showAndWait();
                        if (delAlert.getResult()==ButtonType.YES){
                            try {
                                deleteIntake(intake);
                                loadTableData(searchText);
                                setIntakeId();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }


                        }
                    });

            }
            tblIntake.setItems(intakeObList);
        }catch(SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    private boolean deleteIntake(Intake intake) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM intake WHERE id = ?");
        ps.setString(1,intake.getId().trim());
        return ps.executeUpdate()>0;
    }

    private ObservableList<Intake> fetchIntakeData(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList <Intake> intakeObList = FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT i.id,i.name,i.date,p.id,p.name FROM intake i JOIN program p ON p.id=i.program_id WHERE i.name LIKE?");
        ps.setString(1,"%"+searchText+"%");
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            intakeObList.add(new Intake(
                    set.getString(1),
                    set.getDate(3),
                    set.getString(2),
                    set.getString(4)+"-"+set.getString(5)
            ));
        }
        return intakeObList;


    }

    private void setProgrammeData() {
        try {
            ObservableList<String> programsObList =intakeBo.getProgramListForCombo();
            cmbProgram.setItems(programsObList);
        }catch (SQLException|ClassNotFoundException e) {
            e.printStackTrace();
        }


    }



    private void setIntakeId() {
        try {
            txtId.setText(intakeBo.getLastIntakeId());

        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }

    }



    public void newIntakeOnAction(ActionEvent actionEvent) {
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void saveOnAction(ActionEvent actionEvent) {
        Intake intake=new Intake(
                txtId.getText() ,
                Date.from(dteStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) ,
                txtName.getText() ,
                cmbProgram.getValue()
        );
        try{
            if (btnSave.getText().equals("Save")) {

                boolean isSaved=intakeBo.saveIntake(new RequestIntakeDto(
                        txtId.getId(),
                        txtName.getText(),
                        Date.from(dteStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        cmbProgram.getValue()
                ));
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                    setIntakeId();
                    setProgrammeData();
                    clearField();
                    loadTableData(searchText);
                }



            }else{
                boolean isUpdated=intakeBo.updateIntake(new RequestIntakeDto(
                        txtId.getId(),
                        txtName.getText(),
                        Date.from(dteStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        cmbProgram.getValue()));
                if (isUpdated) {

                    new Alert(Alert.AlertType.INFORMATION, "Update"+intake.getId()).show();
                    clearField();
                    loadTableData(searchText);
                    setIntakeId();
                    btnSave.setText("Save");
                }
            }
        }catch (SQLException|ClassNotFoundException e){
            e.printStackTrace();
        }

        
        
    }







    private void clearField() {
        txtName.clear();
        dteStart.setValue(null);
    }

    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
