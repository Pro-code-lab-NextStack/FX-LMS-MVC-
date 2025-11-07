package com.pcl.lms.dao.cutom.impl;

import com.pcl.lms.dao.CrudDao;
import com.pcl.lms.dao.CrudUtill;
import com.pcl.lms.dao.cutom.StudentDao;
import com.pcl.lms.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public boolean save(Student student) throws SQLException, ClassNotFoundException {
      return   CrudUtill.execute("INSERT INTO student VALUES(?,?,?,?,?)",
                student.getId(),
                student.getName(),
                student.getAddress(),
                student.getDob(),
                "@gmail.com"
        );
    }

    @Override
    public boolean update(Student student) {
        return false;
    }

    @Override
    public boolean delete(String studentId) throws SQLException, ClassNotFoundException {

       return CrudUtill.execute("DELETE FROM student WHERE id=?",studentId);
    }

    @Override
    public String findById(String s) {
        return "";
    }

    @Override
    public List<Student> findAll() {
        return List.of();
    }

    @Override
    public List<Student> findByName(String searchText) throws SQLException, ClassNotFoundException {
       List<Student> students = new ArrayList<>();
        ResultSet set = CrudUtill.execute("SELECT * FROM student WHERE name LIKE?", "%" + searchText + "%");
        while (set.next()) {
            students.add(new Student(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getDate(4),
                    set.getString(5)
            ));
        }
        return students;
    }
}
