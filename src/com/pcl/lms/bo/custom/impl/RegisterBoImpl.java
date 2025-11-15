package com.pcl.lms.bo.custom.impl;

import com.pcl.lms.bo.custom.RegisterBo;
import com.pcl.lms.dao.DaoFactory;
import com.pcl.lms.dao.custom.StudentDao;
import com.pcl.lms.entity.Student;
import com.pcl.lms.utill.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterBoImpl implements RegisterBo {
    StudentDao studentDao= DaoFactory.getInstance().getDao(DaoType.STUDENT);
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
}
