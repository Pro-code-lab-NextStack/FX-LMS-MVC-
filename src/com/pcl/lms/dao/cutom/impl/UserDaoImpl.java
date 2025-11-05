package com.pcl.lms.dao.cutom.impl;

import com.pcl.lms.DB.DbConnection;
import com.pcl.lms.dao.CrudUtill;
import com.pcl.lms.dao.cutom.UserDao;
import com.pcl.lms.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User findByEmail(String email) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtill.execute("SELECT * FROM user WHERE email=?", email);
        if(set.next()){
            return new User(
                    set.getString("email"),
                    set.getString("fulll_name"),
                    set.getInt("age"),
                    set.getString("password")
            );
        }
        return null;
    }

    @Override
    public boolean save(User user) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute
                ("INSERT INTO user VALUES(?,?,?,?)",
                        user.getEmail(), user.getFullName(), user.getAge(), user.getPassword());

    }

    @Override
    public boolean update(User user) {
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
    public List<User> findAll() {
        return List.of();
    }
}
