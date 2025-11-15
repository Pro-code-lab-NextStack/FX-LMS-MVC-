package com.pcl.lms.bo.custom.impl;

import com.pcl.lms.bo.custom.IntakeBo;
import com.pcl.lms.dao.DaoFactory;
import com.pcl.lms.dao.custom.IntakeDao;
import com.pcl.lms.dto.request.RequestIntakeDto;
import com.pcl.lms.dto.response.ResponseIntakeDto;
import com.pcl.lms.entity.Intake;
import com.pcl.lms.entity.Program;
import com.pcl.lms.utill.DaoType;
import com.pcl.lms.utill.tools.IdGenarator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class IntakeBoImpl implements IntakeBo {
    IntakeDao intakeDao= DaoFactory.getInstance().getDao(DaoType.INTAKE);
    @Override
    public boolean saveIntake(RequestIntakeDto requestIntakeDto) throws SQLException, ClassNotFoundException {
        System.out.println(  requestIntakeDto.getIntakeId()+"from bo");
        return    intakeDao.save(new Intake(
                requestIntakeDto.getIntakeId(),
                requestIntakeDto.getIntakeName(),
                Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(requestIntakeDto.getDate())),
            splitId(requestIntakeDto.getProgram())
        ));
    }

    @Override
    public boolean updateIntake(RequestIntakeDto requestIntakeDto) throws SQLException, ClassNotFoundException {
        return  intakeDao.update(
                new Intake(
                        requestIntakeDto.getIntakeId(),
                        requestIntakeDto.getIntakeName(),
                        Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(requestIntakeDto.getDate())),
                        splitId(requestIntakeDto.getProgram())
                ));

    }
    private String splitId(String value){
        String[] split = value.split("-");
        return split[0]+"-"+split[1];
    }

    @Override
    public boolean deleteIntake(String intakeId) throws SQLException, ClassNotFoundException {
        return intakeDao.delete(intakeId);
    }

    @Override
    public String getLastIntakeId() throws SQLException, ClassNotFoundException {
        Intake lastIntake = intakeDao.getLastIntake();

        if (lastIntake != null) {
           return IdGenarator.generateId(lastIntake.getId());
        }
        return "I-1";

    }

    @Override
    public ObservableList<String> getProgramListForCombo() throws SQLException, ClassNotFoundException {
        ObservableList<String> programObList= FXCollections.observableArrayList();
        List<Program> programList = intakeDao.getProgramList();

        for (Program pro:programList){
            programObList.add(pro.getId()+"-"+pro.getName());
        }
        return programObList;
    }

    @Override
    public List<ResponseIntakeDto> fetchIntakeByName(String name) throws SQLException, ClassNotFoundException {
        String searchText="%"+name+"%";
        List<Intake> intakeByName = intakeDao.getIntakeByName(searchText);
        List<ResponseIntakeDto> responseIntakeDtoList = new ArrayList<>();
        for (Intake intake:intakeByName) {
            responseIntakeDtoList.add(new ResponseIntakeDto(
                    intake.getId(),
                    intake.getName(),
                    Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(intake.getDate())),
                    intake.getProgramId()
            ));
        }
        return responseIntakeDtoList;
    }
}
