package com.pcl.lms.controller;

import com.pcl.lms.DB.Database;
import com.pcl.lms.model.Modules;
import com.pcl.lms.model.Programme;
import com.pcl.lms.model.Teacher;
import com.pcl.lms.view.tm.ModulesTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

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
    public TableView tblProgramme;
    public TableColumn colProgrammeId;
    public TableColumn colProgrammeName;
    public TableColumn colTeacher;
    public TableColumn colModuleList;
    public TableColumn colCost;
    public TableColumn colOption;
    public TextField txtSearch;
    static ArrayList <Modules> modList=new ArrayList<>();
    public void initialize() {
        colModuleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colModuleName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colModuleRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setModuleTableData();
        setProgrammeId();
        setTeacher();
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
    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }

    public void newProgramOnAction(ActionEvent actionEvent) {
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
       ObservableList<ModulesTm> list = FXCollections.observableArrayList();
        for (Modules modules:modList){
            Button btn=new Button("Delete");
            list.add(new ModulesTm(
                    modules.getId(),
                    modules.getName(),
                    btn

            ));

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
}
