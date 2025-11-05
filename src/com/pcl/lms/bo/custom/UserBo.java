package com.pcl.lms.bo.custom;

import com.pcl.lms.bo.SuperBo;
import com.pcl.lms.dto.request.RequestUserDto;
import com.pcl.lms.dto.response.ResponseUserDto;
import com.pcl.lms.entity.User;

import java.sql.SQLException;

public interface UserBo extends SuperBo {
    public boolean registerUser(RequestUserDto requestUserDto) throws SQLException, ClassNotFoundException;
    public ResponseUserDto  login(String email, String password) throws SQLException, ClassNotFoundException;
}
