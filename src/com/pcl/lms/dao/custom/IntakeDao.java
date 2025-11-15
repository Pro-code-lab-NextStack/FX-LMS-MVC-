package com.pcl.lms.dao.custom;

import com.pcl.lms.dao.CrudDao;
import com.pcl.lms.entity.Intake;
import com.pcl.lms.entity.Program;

import java.sql.SQLException;
import java.util.List;

public interface IntakeDao extends CrudDao<Intake,String> {
    public Intake getLastIntake() throws SQLException, ClassNotFoundException;
    public List<Program> getProgramList() throws SQLException, ClassNotFoundException;
    public List<Intake> getIntakeByName(String searchText) throws SQLException, ClassNotFoundException;

}
