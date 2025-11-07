package com.pcl.lms.dao.cutom;

import com.pcl.lms.dao.CrudDao;
import com.pcl.lms.entity.Teacher;

import java.sql.SQLException;
import java.util.List;

public interface TeacherDao extends CrudDao<Teacher,String> {
    public List<Teacher> fetchTeacherByName(String name) throws SQLException, ClassNotFoundException;
}
