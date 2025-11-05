package com.pcl.lms.bo.custom;

import com.pcl.lms.bo.SuperBo;
import com.pcl.lms.dto.request.RequestStudentDto;

import java.sql.SQLException;

public interface StudentBo extends SuperBo {
    public boolean saveStudent(RequestStudentDto requestStudentDto) throws SQLException, ClassNotFoundException;
}
