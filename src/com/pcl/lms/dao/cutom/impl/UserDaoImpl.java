package com.pcl.lms.dao.cutom.impl;

import com.pcl.lms.DB.DbConnection;
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
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE email=?");
        ps.setString(1,email);
        ResultSet set = ps.executeQuery();
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
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO user VALUES(?,?,?,?)");
        ps.setString(1, user.getEmail());
        ps.setString(2,user.getFullName());
        ps.setInt(3,user.getAge());
        ps.setString(4,user.getPassword());
        return ps.executeUpdate()>0;
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
