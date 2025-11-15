package com.pcl.lms.dao.custom;

import com.pcl.lms.dao.CrudDao;
import com.pcl.lms.entity.Program;

import java.sql.SQLException;
import java.util.List;

public interface ProgramDao extends CrudDao <Program,String> {
    public List<Program> findProgramByName(String searchText) throws SQLException, ClassNotFoundException;
}
