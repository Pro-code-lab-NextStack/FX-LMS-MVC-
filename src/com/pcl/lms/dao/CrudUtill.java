package com.pcl.lms.dao;

import com.pcl.lms.DB.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtill {

    public static <T>T execute(String sql,Object ...params) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        for (int i=0;i<params.length;i++){
            ps.setObject(i+1,params[i]);
        }
        if (sql.startsWith("SELECT")){
          return (T)  ps.executeQuery();
        }
      return (T)  (Boolean)(ps.executeUpdate()>0);

    }
}
