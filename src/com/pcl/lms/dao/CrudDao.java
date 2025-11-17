package com.pcl.lms.dao;

import com.pcl.lms.model.User;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T,ID> extends SuperDao {
    public boolean save(T t) throws SQLException, ClassNotFoundException;
    public boolean update(T t) throws SQLException, ClassNotFoundException;
    public boolean delete(ID id) throws SQLException, ClassNotFoundException;
    public ID findById(ID id);
    public List<T> findAll() throws SQLException, ClassNotFoundException;
}
