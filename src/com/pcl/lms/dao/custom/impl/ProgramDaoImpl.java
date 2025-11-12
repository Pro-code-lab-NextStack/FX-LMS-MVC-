package com.pcl.lms.dao.custom.impl;

import com.pcl.lms.dao.CrudUtill;
import com.pcl.lms.dao.custom.ProgramDao;
import com.pcl.lms.entity.Program;

import java.sql.SQLException;
import java.util.List;

public class ProgramDaoImpl implements ProgramDao {
    @Override
    public boolean save(Program program) throws SQLException, ClassNotFoundException {
      return   CrudUtill.execute("INSERT INTO program VALUES (?,?,?,?)",
                program.getId(),
                program.getName(),
                program.getCost(),
                program.getTeacherId()

        );
    }

    @Override
    public boolean update(Program program) throws SQLException, ClassNotFoundException {
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
    public List<Program> findAll() {
        return List.of();
    }
}
