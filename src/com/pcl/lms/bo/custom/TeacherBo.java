package com.pcl.lms.bo.custom;

import com.pcl.lms.dto.request.RequestTeacherDto;

import java.sql.SQLException;

public interface TeacherBo {
    public boolean saveTeacher(RequestTeacherDto requestTeacherDto) throws SQLException, ClassNotFoundException;
}
