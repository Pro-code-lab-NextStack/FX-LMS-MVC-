package com.pcl.lms.dao.custom;

import com.pcl.lms.dao.CrudDao;
import com.pcl.lms.entity.Registration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RegisterDao extends CrudDao<Registration,String> {
    public boolean isExists(String id) throws SQLException, ClassNotFoundException;
    public boolean deleteByTransaction(String id, Connection conn) throws SQLException;
}
