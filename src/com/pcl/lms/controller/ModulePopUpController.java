package com.pcl.lms.controller;

import com.pcl.lms.model.Modules;
import com.pcl.lms.view.tm.ModulesTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class ModulePopUpController {
    public AnchorPane context;
    public ListView<String> lstModule;
    ObservableList<String> moduleOblist= FXCollections.observableArrayList();

    public void initialize(){
        setModuleList();

    }

    private void setModuleList() {
        for (ModulesTm tempMod:ProgramManagementFormController.list){
            moduleOblist.add(tempMod.getId()+" : "+tempMod.getName()) ;
        }
        lstModule.setItems(moduleOblist);
    }
}
