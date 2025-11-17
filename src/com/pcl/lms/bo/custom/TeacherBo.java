package com.pcl.lms.bo.custom;

import com.pcl.lms.dto.request.RequestTeacherDto;

import java.sql.SQLException;
import java.util.List;

public interface TeacherBo {
    public boolean saveTeacher(RequestTeacherDto requestTeacherDto) throws SQLException, ClassNotFoundException;
    public List<RequestTeacherDto> getTeachers(String searchText) throws SQLException, ClassNotFoundException;
    public boolean updateTeacher(RequestTeacherDto requestTeacherDto) throws SQLException, ClassNotFoundException;
    public boolean deleteTeacher(String id) throws SQLException, ClassNotFoundException;
}
