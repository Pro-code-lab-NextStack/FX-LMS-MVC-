package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
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
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Teacher t:Database.teacherTable){
            list.add(t.getId().trim()+"-"+t.getName().trim());
        }
        cbxTeacher.setItems(list);

    }

    private void setProgrammeId() {

        if (!Database.programmeTable.isEmpty()) {
            Programme programme = Database.programmeTable.get(Database.programmeTable.size() - 1);

            String programmeId = programme.getProgrammeId();
            String[] splittedId = programmeId.split("-");
            String splittedLastCharacterAsString = splittedId[1];
            int lastDigit = Integer.parseInt(splittedLastCharacterAsString);
            lastDigit++;
            String genaratedID = "P-" + lastDigit;
            txtProgramId.setText(genaratedID);

        }else {
            txtProgramId.setText("P-1");
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String [] selectedModules=new String[modList.size()];
        int pointer=0;
        for(Modules mod:modList){
           selectedModules[pointer] =mod.getName();
           pointer++;
        }
        if (btnSave.getText().equals("Save")) {
            Database.programmeTable.add(new Programme(
                    txtProgramId.getText(),
                    txtProgramName.getText(),
                    Double.parseDouble(txtCost.getText()),
                    cbxTeacher.getValue(),
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
