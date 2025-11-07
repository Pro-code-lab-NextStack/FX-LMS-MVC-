package com.pcl.lms.dao.cutom.impl;

import com.pcl.lms.dao.CrudUtill;
import com.pcl.lms.dao.cutom.TeacherDao;
import com.pcl.lms.entity.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public List<Teacher> fetchTeacherByName(String name) throws SQLException, ClassNotFoundException {
        List<Teacher> teacherList=new ArrayList <>();
        ResultSet set = CrudUtill.execute("SELECT * FROM teacher WHERE name LIKE ?", name);
        while (set.next()) {
            teacherList.add(new Teacher(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4)
            ));
        }
        return teacherList;
    }
}
