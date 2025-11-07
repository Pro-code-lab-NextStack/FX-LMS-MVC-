package com.pcl.lms.bo.custom.impl;

import com.pcl.lms.bo.custom.StudentBo;
import com.pcl.lms.dao.DaoFactory;
import com.pcl.lms.dao.cutom.impl.StudentDaoImpl;
import com.pcl.lms.dao.cutom.impl.UserDaoImpl;
import com.pcl.lms.dto.request.RequestStudentDto;
import com.pcl.lms.dto.response.ResponseStudentDto;
import com.pcl.lms.entity.Student;
import com.pcl.lms.env.Session;
import com.pcl.lms.utill.DaoType;
import com.pcl.lms.view.tm.StudentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentBoImpl implements StudentBo {
    StudentDaoImpl studentDao= DaoFactory.getInstance().getDao(DaoType.STUDENT);
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
       return studentDao.delete(studentId);
    }
}
