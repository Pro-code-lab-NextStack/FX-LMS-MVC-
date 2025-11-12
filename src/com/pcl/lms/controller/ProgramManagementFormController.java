package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.DB.DbConnection;
import com.pcl.lms.bo.BoFactory;
import com.pcl.lms.bo.custom.ProgrammeBo;
import com.pcl.lms.dto.request.RequestProgrameDto;
import com.pcl.lms.model.Modules;
import com.pcl.lms.model.Programme;
import com.pcl.lms.model.Teacher;
import com.pcl.lms.utill.BoType;
import com.pcl.lms.view.tm.ModulesTm;
import com.pcl.lms.view.tm.ProgrammeTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    public static String programIdForModules;


    ProgrammeBo programmeBo= BoFactory.getInstance().getBo(BoType.PROGRAME);
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
    try {
        ObservableList<ProgrammeTm> observableList= fetchProgramDetails(searchText);


        tblProgramme.setItems(observableList);
    }catch (SQLException|ClassNotFoundException e){
        e.printStackTrace();
    }

    }

    private ObservableList<ProgrammeTm> fetchProgramDetails(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<ProgrammeTm> programObList=FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement ps = connection.prepareStatement
                ("SELECT p.id,p.name,p.cost,t.name,t.id FROM program p INNER JOIN teacher t ON t.id=p.teacher_id WHERE p.name LIKE ?");
        ps.setString(1,"%"+searchText+"%");
        ResultSet set = ps.executeQuery();
        String  programmeId;
        while (set.next()){
            programmeId=set.getString(1);
            Button btnModule=new Button("Module");
            Button btnDelete=new Button("Delete");
            ProgrammeTm tm=new ProgrammeTm
                    (set.getString(1),
                            set.getString(2),
                            set.getString(5)+"-"+set.getString(4),
                            btnModule,set.getDouble(3),btnDelete);
            programObList.add(tm);




            btnDelete.setOnAction(actionEvent -> {
                Alert alert = 
                        new Alert(Alert.AlertType.CONFIRMATION, 
                                "Do you want to delete this program ? ", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult()==ButtonType.YES){
                    try {
                      boolean isDeleted=  deleteProgram(tm);
                      if (isDeleted){
                          loadProgrammeData(searchText);
                          setProgrammeId();
                          new Alert(Alert.AlertType.INFORMATION,"Program Deleted").show();
                      }
                    } catch (SQLException|ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                
            });
            programIdForModules = programmeId;
            btnModule.setOnAction((event)->{
        
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pcl/lms/view/ModulePopUP.FXML"));
                    Parent load = loader.load();
                    ModulePopUpController controller = loader.getController();


                    Stage stage = new Stage();
                    stage.setScene(new Scene(load));
                    stage.setTitle("Module List");
                    stage.show();

                } catch (IOException  e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return programObList;
    }

    private boolean deleteProgram(ProgrammeTm tm) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            try(PreparedStatement ps1=connection.prepareStatement
                    ("DELETE FROM module_has_program WHERE program_id=?")){
                ps1.setString(1, tm.getProgrammeId());
                ps1.executeUpdate();
            }
            boolean programDeleted;
            try (PreparedStatement ps2=connection.prepareStatement("DELETE FROM program WHERE id=?")){
                ps2.setString(1, tm.getProgrammeId());
                programDeleted=ps2.executeUpdate()>0;
            }
            try(PreparedStatement ps3=connection.prepareStatement
                    ("DELETE FROM module WHERE id NOT IN (SELECT DISTINCT module_id FROM module_has_program)")){
                ps3.executeUpdate();
            }
            if(programDeleted){
                connection.commit();
                return true;
            }else {
                connection.rollback();
                return false;
            }

        }catch (Exception e){
            connection.rollback();
            e.printStackTrace();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    private void setTeacher() {
        try{
            ArrayList<String> teacherArr=fetchTeachers();
            ObservableList<String> list = FXCollections.observableArrayList();
            for (String t:teacherArr){
               list.add(t);
            }
            cbxTeacher.setItems(list);
        }catch (Exception e){
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
                programmeBo.saveProgram(
                        new RequestProgrameDto(
                                txtProgramId.getText(),
                                txtProgramName.getText(),
                                Double.parseDouble(txtCost.getText()),
                                cbxTeacher.getValue(),
                                selectedModules

                        )
                );

                setProgrammeId();
                clearFields();
                loadProgrammeData(searchText);
                new Alert(Alert.AlertType.INFORMATION, "Programme Saved").show();
            }else {
               boolean isUpdated= updateProgramme(
                        new Programme( txtProgramId.getText(),
                        txtProgramName.getText(),
                        Double.parseDouble(txtCost.getText()),
                        splitId(cbxTeacher.getValue()),
                        selectedModules));


                if (isUpdated) {

                    new Alert(Alert.AlertType.INFORMATION, "Programme Updated : "+txtProgramId.getText()).show();
                    loadProgrammeData(searchText);
                    clearFields();
                    btnSave.setText("Save");
                }
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }


    }

    private boolean updateProgramme(Programme programme) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("UPDATE program SET name=?,cost=?,teacher_id=? WHERE id=?");
        ps.setString(1,programme.getProgrammeName());
        ps.setDouble(2,programme.getCost());
        ps.setString(3,splitId(programme.getTeacher()));
        ps.setString(4,programme.getProgrammeId());
        return  ps.executeUpdate()>0;
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
