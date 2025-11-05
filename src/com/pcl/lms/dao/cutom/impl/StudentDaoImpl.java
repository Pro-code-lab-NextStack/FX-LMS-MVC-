package com.pcl.lms.dao.cutom.impl;

import com.pcl.lms.dao.CrudUtill;
import com.pcl.lms.dao.cutom.StudentDao;
import com.pcl.lms.entity.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public boolean save(Student student) throws SQLException, ClassNotFoundException {
      return   CrudUtill.execute("INSERT INTO student VALUES(?,?,?,?,?)",
                student.getId(),
                student.getName(),
                student.getAddress(),
                student.getDob(),
                student.getUser_email()
        );
    }

    @Override
    public boolean update(Student student) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public String findById(String s) {
        return "";
    }

    @Override
    public List<Student> findAll() {
        return List.of();
    }
}
