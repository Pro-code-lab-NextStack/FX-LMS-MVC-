package com.pcl.lms.bo.custom;

import com.pcl.lms.bo.SuperBo;

import java.sql.SQLException;
import java.util.List;

public interface RegisterBo extends SuperBo {
    public List<String> findStudentForComboByName(String text   ) throws SQLException, ClassNotFoundException;
}
