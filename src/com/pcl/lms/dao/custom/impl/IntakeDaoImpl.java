package com.pcl.lms.dao.custom.impl;

import com.pcl.lms.dao.CrudUtill;
import com.pcl.lms.dao.custom.IntakeDao;
import com.pcl.lms.entity.Intake;
import com.pcl.lms.entity.Program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntakeDaoImpl implements IntakeDao {
    @Override
    public boolean save(Intake intake) throws SQLException, ClassNotFoundException {
      return   CrudUtill.execute("INSERT INTO intake VALUES(?,?,?,?)",
                intake.getId(),
                intake.getName(),
                intake.getDate(),
                intake.getProgramId()
                );
    }

    @Override
    public boolean update(Intake intake) throws SQLException, ClassNotFoundException {
       return CrudUtill.execute("UPDATE intake SET name=?,date=?,program_id WHERE id=?",
        intake.getName(),
                intake.getDate(),
                intake.getProgramId(),
                intake.getId()
        );
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
      return   CrudUtill.execute("DELETE FROM intake WHERE id=?",s);
    }

    @Override
    public String findById(String s) {
        return "";
    }

    @Override
    public List<Intake> findAll() {
        return List.of();
    }

    @Override
    public Intake getLastIntake() throws SQLException, ClassNotFoundException {
    ResultSet set = CrudUtill.execute("SELECT * FROM intake ORDER BY CAST(SUBSTRING(id,3)AS UNSIGNED)DESC LIMIT 1");
    if (set.next()) {
        return new Intake(
                set.getString(1),
                set.getString(2),
                set.getDate(3),
                set.getString(4)
        );
    }
    return null;

    }

    @Override
    public List<Program> getProgramList() throws SQLException, ClassNotFoundException {
        List<Program> programList = new ArrayList<>();
        ResultSet set = CrudUtill.execute("SELECT * FROM program");
        while (set.next()) {
            programList.add(new Program(
                    set.getString(1),
                    set.getString(2),
                    set.getDouble(3),
                    set.getString(4)
            ));
        }
        return programList;
    }

}
