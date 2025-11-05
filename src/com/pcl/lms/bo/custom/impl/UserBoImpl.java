package com.pcl.lms.bo.custom.impl;

import com.pcl.lms.bo.custom.UserBo;
import com.pcl.lms.dao.DaoFactory;
import com.pcl.lms.dao.SuperDao;
import com.pcl.lms.dao.cutom.impl.UserDaoImpl;
import com.pcl.lms.dto.request.RequestUserDto;
import com.pcl.lms.dto.response.ResponseUserDto;
import com.pcl.lms.entity.User;
import com.pcl.lms.env.Session;
import com.pcl.lms.utill.DaoType;
import com.pcl.lms.utill.security.PasswordManager;

import java.sql.SQLException;

public class UserBoImpl implements UserBo {
    UserDaoImpl user=DaoFactory.getInstance().getDao(DaoType.USER);
    @Override
    public boolean registerUser(RequestUserDto requestUserDto) throws SQLException, ClassNotFoundException {
     boolean isSaved= user.save(new User(
              requestUserDto.getEmail(),
              requestUserDto.getFullName(),
              requestUserDto.getAge(),
              new PasswordManager().encode(requestUserDto.getPassword())
      ));
     if(isSaved){
         return true;
     }
     return false;
    }

    @Override
    public ResponseUserDto login(String email, String password) throws SQLException, ClassNotFoundException {
        User selectedUser = user.findByEmail(email);
        if (selectedUser!=null){
            if (new PasswordManager().check(password,selectedUser.getPassword())){
                Session.setEmail(email);
                return new ResponseUserDto(
                        selectedUser.getEmail(),
                        selectedUser.getFullName(),
                        200,
                        "login successful"
                );
            }else  {
                return new ResponseUserDto(
                        null,
                        null,
                        401,
                        "Incorrect password"
                );
            }
        }
        return null;
    }
}
