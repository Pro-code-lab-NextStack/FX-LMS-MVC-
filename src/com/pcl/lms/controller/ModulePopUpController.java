package com.pcl.lms.controller;

import com.pcl.lms.DB.DbConnection;
import com.pcl.lms.model.Modules;
import com.pcl.lms.view.tm.ModulesTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModulePopUpController {
    public AnchorPane context;
    public ListView<String> lstModule;
    ObservableList<String> moduleOblist= FXCollections.observableArrayList();
    String programmeId;

    public void initialize(){
        this.programmeId=ProgramManagementFormController.programIdForModules;
        setModuleList(programmeId);

    }

    private void setModuleList(String programId) {
        try{
            ObservableList <String> moduleOblist=fetchModules(programId);

            lstModule.setItems(moduleOblist);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    private ObservableList<String> fetchModules(String programId) throws SQLException, ClassNotFoundException {
        ObservableList<String> moduleOblist=FXCollections.observableArrayList();
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT m.name FROM  module_has_program mhp JOIN module m ON mhp.module_id=m.id WHERE mhp.program_id=?");
        System.out.println(programId);
        ps.setString(1, programId);
        ResultSet set = ps.executeQuery();

        while(set.next()){
            moduleOblist.add(set.getString(1));
            System.out.println(set.getString(1));
        }
        return moduleOblist;
    }
}
