package com.pcl.lms.bo.custom.impl;

import com.pcl.lms.bo.custom.ProgrammeBo;
import com.pcl.lms.dao.DaoFactory;
import com.pcl.lms.dao.custom.ProgramDao;
import com.pcl.lms.dto.request.RequestProgrameDto;
import com.pcl.lms.dto.response.ResponseProgramDto;
import com.pcl.lms.entity.Program;
import com.pcl.lms.utill.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgrammeBoImpl implements ProgrammeBo {
    ProgramDao programDao= DaoFactory.getInstance().getDao(DaoType.PROGRAME);
    @Override
    public boolean saveProgram(RequestProgrameDto program) throws SQLException, ClassNotFoundException {

       return programDao.save(new Program(
                program.getId(),
                program.getName(),
                program.getCost(),
                splitId(program.getTeacher())
        ));
    }

    @Override
    public String splitId(String comboText) {
        String[] splittedArr = comboText.split("-");
        return splittedArr[0]+"-"+splittedArr[1];
    }

    @Override
    public List<ResponseProgramDto> fetchProgramByName(String text) throws SQLException, ClassNotFoundException {
      String searchText="%"+text+"%";
        List<Program> programByName = programDao.findProgramByName(searchText);
        List<ResponseProgramDto> responseProgramDtoList = new ArrayList<>();
        for (Program pro:programByName){
            responseProgramDtoList.add(new ResponseProgramDto(
                   pro.getId(),
                   pro.getName(),
                   pro.getCost(),
                   pro.getTeacherId()
            ));

        }
        return responseProgramDtoList;
    }
}
