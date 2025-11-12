package com.pcl.lms.dao.custom.impl;

import com.pcl.lms.dao.CrudUtill;
import com.pcl.lms.dao.custom.ProgramDao;
import com.pcl.lms.entity.Program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public List<Program> findProgramByName(String searchText) throws SQLException, ClassNotFoundException {
          ResultSet set= CrudUtill.execute(
                  "SELECT p.id,p.name,p.cost,t.id,t.name FROM program p INNER JOIN  teacher t ON p.teacher_id= t.id WHERE  p.name LIKE ? "
                  ,searchText);
          List <Program> programList=new ArrayList<>();

          while (set.next()){
              programList.add(new Program(
                      set.getString(1),
                      set.getString(2),
                      set.getDouble(3),
                      set.getString(4)+"-"+set.getString(5)
              ));
          }
          return programList;
    }
}
