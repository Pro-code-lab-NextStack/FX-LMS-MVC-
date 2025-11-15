package com.pcl.lms.dao.custom.impl;

import com.pcl.lms.dao.CrudUtill;
import com.pcl.lms.dao.custom.RegisterDao;
import com.pcl.lms.entity.Registration;

import java.sql.SQLException;
import java.util.List;

public class RegisterDaoImpl implements RegisterDao {
    @Override
    public boolean save(Registration registration) throws SQLException, ClassNotFoundException {
      return   CrudUtill.execute("INSERT INTO enroll VALUES(?,?,?)",
                registration.getProgramId(),
                registration.getStudentId(),
                registration.isPaid()
                );
    }

    @Override
    public boolean update(Registration registration) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String findById(String s) {
        return "";
    }

    @Override
    public List<Registration> findAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }
}
