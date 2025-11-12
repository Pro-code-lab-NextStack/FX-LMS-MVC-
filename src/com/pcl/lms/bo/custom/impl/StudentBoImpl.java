package com.pcl.lms.bo.custom.impl;

import com.pcl.lms.bo.custom.StudentBo;
import com.pcl.lms.dao.DaoFactory;

import com.pcl.lms.dao.custom.impl.StudentDaoImpl;
import com.pcl.lms.dto.request.RequestStudentDto;
import com.pcl.lms.dto.response.ResponseStudentDto;
import com.pcl.lms.entity.Student;
import com.pcl.lms.env.Session;
import com.pcl.lms.utill.DaoType;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StudentBoImpl implements StudentBo {
    StudentDaoImpl studentDao= DaoFactory.getInstance().getDao(DaoType.STUDENT);
   // EnrollDaoImpl enrollDao=DaoFactory.getInstance().getDao(DaoType.ENROLL);
    @Override
    public boolean saveStudent(RequestStudentDto requestStudentDto) throws SQLException, ClassNotFoundException {
       return studentDao.save(new Student(
                requestStudentDto.getId(),
                requestStudentDto.getName(),
                requestStudentDto.getAddress(),
                Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(requestStudentDto.getDob())),
                Session.getEmail()
        ));
    }

    @Override
    public List<ResponseStudentDto> getStudents(String searchText) throws SQLException, ClassNotFoundException {
        List<Student> students = studentDao.findByName(searchText);
        List <ResponseStudentDto>responseStudentDtoList=new ArrayList<>();
        for (Student student : students) {

            responseStudentDtoList.add(new ResponseStudentDto(
                    student.getId(),
                    student.getName(),
                    student.getAddress(),
                   student.getDob().toString(),
                    student.getUser_email()



            ));

        }
        return responseStudentDtoList;
    }

    @Override
    public boolean deleteStudent(String studentId) throws SQLException, ClassNotFoundException {
       /* Connection connection= DbConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isStudentDeleted = studentDao.delete(studentId);
            if (isEnrollDeleted && isStudentDeleted){
                connection.commit();
                return true;
            }else {
                connection.rollback();
                return false;
            }
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }finally {
            connection.setAutoCommit(true);
        }*/
        return false;
    }

    @Override
    public boolean updateStudent(RequestStudentDto requestStudentDto) throws SQLException, ClassNotFoundException {
     return   studentDao.update(new Student(
             requestStudentDto.getId(),
             requestStudentDto.getName(),
             requestStudentDto.getAddress(),
               Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(requestStudentDto.getDob())),
             "@gmail.com"

       ));
    }
}
