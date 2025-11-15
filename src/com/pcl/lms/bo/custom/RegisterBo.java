package com.pcl.lms.bo.custom;

import com.pcl.lms.bo.SuperBo;
import com.pcl.lms.dto.request.RequestRegisterDto;

import java.sql.SQLException;
import java.util.List;

public interface RegisterBo extends SuperBo {
    public List<String> findStudentForComboByName(String text) throws SQLException, ClassNotFoundException;
    public List<String> findProgramtForCombo() throws SQLException, ClassNotFoundException;
    public boolean registration(RequestRegisterDto requestRegisterDto) throws SQLException, ClassNotFoundException;

    }

