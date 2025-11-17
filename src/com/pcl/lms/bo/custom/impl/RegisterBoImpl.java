package com.pcl.lms.bo.custom.impl;

import com.pcl.lms.bo.custom.RegisterBo;
import com.pcl.lms.dao.DaoFactory;
import com.pcl.lms.dao.custom.ProgramDao;
import com.pcl.lms.dao.custom.RegisterDao;
import com.pcl.lms.dao.custom.StudentDao;
import com.pcl.lms.dto.request.RequestRegisterDto;
import com.pcl.lms.entity.Program;
import com.pcl.lms.entity.Registration;
import com.pcl.lms.entity.Student;
import com.pcl.lms.utill.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterBoImpl implements RegisterBo {
    StudentDao studentDao= DaoFactory.getInstance().getDao(DaoType.STUDENT);
    ProgramDao programDao=DaoFactory.getInstance().getDao(DaoType.PROGRAME);
    RegisterDao registerDao=DaoFactory.getInstance().getDao(DaoType.REGISTER);
    @Override
    public List<String> findStudentForComboByName(String text) throws SQLException, ClassNotFoundException {
       String searchText = "%" + text + "%";
        List<Student> byName = studentDao.findByName(searchText);
        List<String> studentDetailsForCombo = new ArrayList<>();
        for (Student st:byName) {
            studentDetailsForCombo.add(st.getId()+"-"+st.getName());
        }
        return studentDetailsForCombo;
    }

    @Override
    public List<String> findProgramtForCombo() throws SQLException, ClassNotFoundException {
      String searchText = "%%";
        List<Program> programByName = programDao.findAll();
        List<String> programDetailsForCombo = new ArrayList<>();
        for (Program p:programByName) {
            programDetailsForCombo.add(p.getId()+"-"+p.getName());
        }
        return programDetailsForCombo;
    }

    @Override
    public boolean registration(RequestRegisterDto requestRegisterDto) throws SQLException, ClassNotFoundException {
       return registerDao.save(new Registration(
               split( requestRegisterDto.getStudent()),
                split(requestRegisterDto.getProgram()),
                requestRegisterDto.isPaid()
        ));
    }

    private String split(String student) {
        String[] split = student.split("-");
        return split[0]+"-"+split[1];
    }


}
