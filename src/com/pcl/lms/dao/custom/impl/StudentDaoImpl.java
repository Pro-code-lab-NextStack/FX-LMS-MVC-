package com.pcl.lms.dao.custom.impl;

import com.pcl.lms.dao.CrudUtill;
import com.pcl.lms.dao.custom.StudentDao;
import com.pcl.lms.entity.Student;
import com.pcl.lms.env.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public  class StudentDaoImpl implements StudentDao {
    @Override
    public boolean save(Student student) throws SQLException, ClassNotFoundException {
      return   CrudUtill.execute("INSERT INTO student VALUES(?,?,?,?,?)",
                student.getId(),
                student.getName(),
                student.getAddress(),
                student.getDob(),
              Session.getEmail()
        );
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException{
      return CrudUtill.execute("DELETE FROM student WHERE id=?",id);
    }

    @Override
    public boolean update(Student student) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("UPDATE student SET name=?,address=?,dob=? WHERE id=?",
                student.getName(),
                student.getAddress(),
                student.getDob(),
                student.getId()
        );
    }


    public boolean delete(String studentId, Connection connection) throws SQLException, ClassNotFoundException {

        PreparedStatement ps = connection.prepareStatement("DELETE FROM student WHERE id=?");
        ps.setString(1, studentId);
        return ps.executeUpdate()>0;
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

    @Override
    public boolean deleteByTransaction(String id, Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM student WHERE id=?");
        ps.setString(1, id);
       return ps.executeUpdate()>0;
    }
}
