package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.DB.DbConnection;
import com.pcl.lms.model.Modules;
import com.pcl.lms.model.Programme;
import com.pcl.lms.model.Teacher;
import com.pcl.lms.view.tm.ModulesTm;
import com.pcl.lms.view.tm.ProgrammeTm;
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
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class ProgramManagementFormController {
    public TextField txtProgramId;
    public TextField txtProgramName;
    public TextField txtCost;
    public ComboBox<String> cbxTeacher;
    public TextField txtModules;
    public TableView<ModulesTm> tblModule;
    public TableColumn<ModulesTm,Integer> colModuleId;
    public TableColumn <ModulesTm,String> colModuleName;
    public TableColumn<ModulesTm,Button> colModuleRemove;
    public Button btnSave;
    public TableView<ProgrammeTm> tblProgramme;
    public TableColumn<ProgrammeTm,String> colProgrammeId;
    public TableColumn <ProgrammeTm,String> colProgrammeName;
    public TableColumn <ProgrammeTm,String> colTeacher;
    public TableColumn<ProgrammeTm,Button> colModuleList;
    public TableColumn <ProgrammeTm,Double> colCost;
    public TableColumn <ProgrammeTm,Button> colOption;
    public AnchorPane context;
    public TextField txtSearch;
     ArrayList <Modules> modList=new ArrayList<>();
   static ObservableList<ModulesTm> list = FXCollections.observableArrayList();
   private String searchText="";

    public void initialize() {
        colModuleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModuleName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colModuleRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));

        colProgrammeId.setCellValueFactory(new PropertyValueFactory<>("programmeId"));
        colProgrammeName.setCellValueFactory(new PropertyValueFactory<>("programmeName"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        colModuleList.setCellValueFactory(new PropertyValueFactory<>("btnModules"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
        tblProgramme.getSelectionModel().selectedItemProperty().addListener
                ((observableValue,oldValue,newValue)->{
                        if (newValue!=null){
                            setData((ProgrammeTm)newValue);
                        }
        });
        txtSearch.textProperty().addListener((observable,oldValue,newValue)->{
                searchText=newValue;
                loadProgrammeData(searchText);
        });
        setModuleTableData();
        loadProgrammeData(searchText);
        setProgrammeId();
        setTeacher();
    }

    private void setData(ProgrammeTm tm) {
        btnSave.setText("Update");
        txtProgramId.setText(tm.getProgrammeId());
        txtProgramName.setText(tm.getProgrammeName());
        cbxTeacher.setValue(tm.getTeacher());
        txtCost.setText(Double.toString(tm.getCost()));
    }

    private void loadProgrammeData(String searchText) {
        ObservableList <ProgrammeTm> programsObList=FXCollections.observableArrayList();
        for (Programme temp:Database.programmeTable){
            if (temp.getProgrammeName().contains(searchText)){
                Button btnModule=new Button("Module");
                Button btnDelete=new Button("Delete");
                programsObList.add(
                        new ProgrammeTm(
                                temp.getProgrammeId(),
                                temp.getProgrammeName(),
                                temp.getTeacher(),
                                btnModule,
                                temp.getCost(),
                                btnDelete
                        )
                );
                btnDelete.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure",ButtonType.YES,ButtonType.NO);
                    alert.showAndWait();
                    if (alert.getResult()==ButtonType.YES){
                        Database.programmeTable.remove(temp);
                        loadProgrammeData(searchText);
                        setProgrammeId();
                        new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
                    }
                });
                btnModule.setOnAction(event -> {
                    try{
                        Stage stage = new Stage();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/ModulePopUp.fxml"))));
                        stage.show();
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }

                });
            }



        }
        tblProgramme.setItems(programsObList);
    }

    private void setTeacher() {
        try{
            ArrayList<String> teacherArr=fetchTeachers();
            ObservableList<String> list = FXCollections.observableArrayList();
            for (String t:teacherArr){
               list.add(t);
            }
            cbxTeacher.setItems(list);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }


    }

    private ArrayList<String> fetchTeachers() throws SQLException, ClassNotFoundException {
        ArrayList<String> teachers=new ArrayList<>();
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM teacher");
        ResultSet set = ps.executeQuery();
        while (set.next()){
            teachers.add(set.getString("id")+"-"+set.getString("name"));
        }
        return teachers;
    }

    private void setProgrammeId() {

        try {
            String lastProgramIdAsString=  getLastProgramId();
            if (lastProgramIdAsString!=null) {



                String[] splittedId = lastProgramIdAsString.split("-");
                String splittedLastCharacterAsString = splittedId[1];
                int lastDigit = Integer.parseInt(splittedLastCharacterAsString);
                lastDigit++;
                String genaratedID = "P-" + lastDigit;
                txtProgramId.setText(genaratedID);

            }else {
                txtProgramId.setText("P-1");
            }

        }catch (SQLException |ClassNotFoundException e){
            e.printStackTrace();
        }



    }

    private String getLastProgramId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT id FROM program ORDER BY  CAST(SUBSTRING(id,3)AS UNSIGNED)DESC LIMIT 1");
        ResultSet set = ps.executeQuery();
        if (set.next()) {
            return set.getString("id");
        }
        return null;
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String [] selectedModules=new String[modList.size()];
        int pointer=0;
        for(Modules mod:modList){
           selectedModules[pointer] =mod.getName();
           pointer++;
        }
        try {
            if (btnSave.getText().equals("Save")) {
                boolean isSaved=saveProgram(new Programme(
                        txtProgramId.getText(),
                        txtProgramName.getText(),
                        Double.parseDouble(txtCost.getText()),
                        splitId(cbxTeacher.getValue()),
                        selectedModules
                ));

                setProgrammeId();
                clearFields();
                loadProgrammeData(searchText);
                new Alert(Alert.AlertType.INFORMATION, "Programme Saved").show();
            }else {
                Optional<Programme> selectedProgramme = Database.programmeTable.stream().filter
                        (e -> e.getProgrammeId().equals(txtProgramId.getText())).findFirst();

                if (selectedProgramme.isPresent()) {
                    selectedProgramme.get().setProgrammeName(txtProgramName.getText());
                    selectedProgramme.get().setCost(Double.parseDouble(txtCost.getText()));
                    selectedProgramme.get().setTeacher(cbxTeacher.getValue());
                    selectedProgramme.get().setModule(selectedModules);
                    new Alert(Alert.AlertType.INFORMATION, "Programme Updated"+txtProgramId.getText()).show();
                    loadProgrammeData(searchText);
                    clearFields();
                    btnSave.setText("Save");
                }
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }


    }

    private String splitId(String value) {
        String[] splittedArr = value.split("-");
        return splittedArr[0]+"-"+splittedArr[1];
    }

    private boolean saveProgram(Programme programme) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try (
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO program (id, name, cost, teacher_id) VALUES (?, ?, ?, ?)"
                )
        ) {
            ps.setString(1, programme.getProgrammeId());
            ps.setString(2, programme.getProgrammeName());
            ps.setDouble(3, programme.getCost());
            ps.setString(4, programme.getTeacher());

            if (ps.executeUpdate() == 0) {
                connection.rollback();
                return false;
            }

            for (String module : programme.getModule()) {
                try (
                        PreparedStatement ps2 = connection.prepareStatement(
                                "INSERT INTO module (name) VALUES (?)",
                                Statement.RETURN_GENERATED_KEYS
                        )
                ) {
                    ps2.setString(1, module);

                    if (ps2.executeUpdate() == 0) {
                        connection.rollback();
                        return false;
                    }

                    try (ResultSet set = ps2.getGeneratedKeys()) {
                        if (set.next()) {
                            int moduleId = set.getInt(1);
                            try (
                                    PreparedStatement ps3 = connection.prepareStatement(
                                            "INSERT INTO module_has_program (module_id, program_id) VALUES (?, ?)"
                                    )
                            ) {
                                ps3.setInt(1, moduleId);
                                ps3.setString(2, programme.getProgrammeId());

                                if (ps3.executeUpdate() == 0) {
                                    connection.rollback();
                                    return false;
                                }
                            }
                        } else {
                            connection.rollback();
                            return false;
                        }
                    }
                }
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    private void clearFields() {
        txtCost.clear();
        txtProgramName.clear();
        txtModules.clear();
        modList.clear();
        setModuleTableData();

        cbxTeacher.setValue("Teachers");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void newProgramOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void addModulesOnAction(ActionEvent actionEvent) {
            if (txtModules.getText().equals(null)) {
                return;
            }
            modList.add(new Modules(txtModules.getText(),getModuleId()));

            setModuleTableData();
            txtModules.clear();

    }

    private void setModuleTableData() {
        list.clear();

        for (Modules modules:modList){
            Button btn=new Button("Delete");
            list.add(new ModulesTm(
                    modules.getId(),
                    modules.getName(),
                    btn

            ));
            btn.setOnAction(event -> {
               Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?..",ButtonType.YES,ButtonType.NO);
               alert.showAndWait();
               if (alert.getResult()==ButtonType.YES){
                   modList.remove(modules);
                   setModuleTableData();
               }
            });

        }

        tblModule.setItems(list);
    }

    private int getModuleId() {
      boolean listEmpty = modList.isEmpty();
      if (listEmpty) {
          return 1;
      }
        Modules lastModule = modList.get(modList.size() - 1);
        int lastId = lastModule.getId();
        lastId++;
        return lastId;
    }
    private void setUi(String location) throws IOException {
        Stage stage =(Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/pcl/lms/view/"+location+".fxml"))));
    }
}
