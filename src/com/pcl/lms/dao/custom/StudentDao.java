package com.pcl.lms.dao.custom;

import com.pcl.lms.dao.CrudDao;
import com.pcl.lms.entity.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface StudentDao extends CrudDao<Student,String> {
    public List<Student> findByName(String searchText) throws SQLException, ClassNotFoundException;
    public boolean deleteByTransaction(String id, Connection conn) throws SQLException;

}
