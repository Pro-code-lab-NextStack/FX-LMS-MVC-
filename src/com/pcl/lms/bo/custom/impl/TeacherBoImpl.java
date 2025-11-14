package com.pcl.lms.bo.custom.impl;

import com.pcl.lms.DB.DbConnection;
import com.pcl.lms.bo.custom.TeacherBo;
import com.pcl.lms.dao.DaoFactory;
import com.pcl.lms.dao.custom.impl.TeacherDaoImpl;
import com.pcl.lms.dto.request.RequestTeacherDto;
import com.pcl.lms.entity.Teacher;
import com.pcl.lms.utill.DaoType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<RequestTeacherDto> getTeachers(String searchText) throws SQLException, ClassNotFoundException {
       String txt="%"+searchText+"%";
        List<Teacher> teachers = teacherDao.fetchTeacherByName(txt);
        List<RequestTeacherDto> requestTeacherDtos = new ArrayList<>();
        for (Teacher teacher:teachers){
           requestTeacherDtos.add( new RequestTeacherDto(
                   teacher.getId(),
                   teacher.getName(),
                   teacher.getContact(),
                   teacher.getAddress()
           ));
        }
        return requestTeacherDtos;
    }

    @Override
    public boolean updateTeacher(RequestTeacherDto requestTeacherDto) throws SQLException, ClassNotFoundException {
     return  teacherDao.update(new Teacher(
                requestTeacherDto.getId(),
                requestTeacherDto.getName(),
                requestTeacherDto.getContact(),
                requestTeacherDto.getAddress()
        ));
    }

    @Override
    public boolean deleteTeacher(String id) throws SQLException, ClassNotFoundException {
        Connection conn= DbConnection.getInstance().getConnection();
        conn.setAutoCommit(false);
        try {
            boolean isDeleted = teacherDao.deleteTeacherById(id, conn);
            if (isDeleted) {
                conn.commit();
                return true;
            }else {
                conn.rollback();
                return false;
            }

        }catch (Exception e){
            conn.rollback();
            throw e;
        }finally {
            conn.setAutoCommit(true);
        }

    }

}
