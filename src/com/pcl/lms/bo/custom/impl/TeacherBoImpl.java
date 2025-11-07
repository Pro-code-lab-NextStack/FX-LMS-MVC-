package com.pcl.lms.bo.custom.impl;

import com.pcl.lms.bo.custom.TeacherBo;
import com.pcl.lms.dao.DaoFactory;
import com.pcl.lms.dao.cutom.impl.TeacherDaoImpl;
import com.pcl.lms.dto.request.RequestTeacherDto;
import com.pcl.lms.entity.Teacher;
import com.pcl.lms.utill.DaoType;

import java.sql.SQLException;

public class TeacherBoImpl implements TeacherBo {
    TeacherDaoImpl  teacherDao = DaoFactory.getInstance().getDao(DaoType.TEACHER);
    @Override
    public boolean saveTeacher(RequestTeacherDto requestTeacherDto) throws SQLException, ClassNotFoundException {
       return teacherDao.save(new Teacher(
                requestTeacherDto.getId(),
                requestTeacherDto.getName(),
                requestTeacherDto.getContact(),
                requestTeacherDto.getAddress()
        ));
    }
}
