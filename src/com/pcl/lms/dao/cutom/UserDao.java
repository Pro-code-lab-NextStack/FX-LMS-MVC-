package com.pcl.lms.dao.cutom;

import com.pcl.lms.dao.CrudDao;
import com.pcl.lms.entity.User;

import java.sql.SQLException;

public interface UserDao extends CrudDao<User, String> {
        public User findByEmail(String email) throws SQLException, ClassNotFoundException;

}
