package com.pcl.lms.bo.custom.impl;

import com.pcl.lms.DB.DbConnection;
import com.pcl.lms.bo.custom.StudentBo;
import com.pcl.lms.dao.DaoFactory;

import com.pcl.lms.dao.custom.RegisterDao;
import com.pcl.lms.dao.custom.impl.StudentDaoImpl;
import com.pcl.lms.dto.request.RequestStudentDto;
import com.pcl.lms.dto.response.ResponseStudentDto;
import com.pcl.lms.entity.Student;
import com.pcl.lms.env.Session;
import com.pcl.lms.utill.DaoType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StudentBoImpl implements StudentBo {
    StudentDaoImpl studentDao= DaoFactory.getInstance().getDao(DaoType.STUDENT);
   RegisterDao registerDao=DaoFactory.getInstance().getDao(DaoType.REGISTER);
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
        System.out.println("St id"+studentId);
        boolean isRegistered = registerDao.isExists(studentId);

        if (isRegistered) {
            Connection conn= DbConnection.getInstance().getConnection();
            conn.setAutoCommit(false);
            try{
                boolean isDeleted = registerDao.deleteByTransaction(studentId, conn);
                if (isDeleted) {
                    boolean isDeleted2 = studentDao.deleteByTransaction(studentId, conn);
                    if (isDeleted2) {
                        conn.commit();
                        return true;
                    }
                }else {
                    conn.rollback();
                    return false;
                }

            }catch (Exception e){
                conn.rollback();
                return false;
            }finally {
                conn.setAutoCommit(true);
            }
        }
          return   studentDao.delete(studentId);



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
