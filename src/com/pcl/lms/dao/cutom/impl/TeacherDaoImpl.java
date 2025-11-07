package com.pcl.lms.dao.cutom.impl;

import com.pcl.lms.dao.CrudUtill;
import com.pcl.lms.dao.cutom.TeacherDao;
import com.pcl.lms.entity.Teacher;

import java.sql.SQLException;
import java.util.List;

public class TeacherDaoImpl implements TeacherDao {
    @Override
    public boolean save(Teacher teacher) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("INSERT INTO teacher VALUES(?,?,?,?)",
                teacher.getId(),
                teacher.getName(),
                teacher.getContact(),
                teacher.getAddress()
                );
    }

    @Override
    public boolean update(Teacher teacher) throws SQLException, ClassNotFoundException {
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
    public List<Teacher> findAll() {
        return List.of();
    }
}
