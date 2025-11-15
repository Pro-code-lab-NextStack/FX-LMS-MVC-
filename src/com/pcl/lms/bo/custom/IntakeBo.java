package com.pcl.lms.bo.custom;

import com.pcl.lms.bo.SuperBo;
import com.pcl.lms.dto.request.RequestIntakeDto;
import com.pcl.lms.dto.response.ResponseIntakeDto;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IntakeBo extends SuperBo {
    public boolean saveIntake(RequestIntakeDto requestIntakeDto) throws SQLException, ClassNotFoundException;
    public boolean updateIntake(RequestIntakeDto requestIntakeDto) throws SQLException, ClassNotFoundException;
    public boolean deleteIntake(String intakeId) throws SQLException, ClassNotFoundException;
    public String getLastIntakeId() throws SQLException, ClassNotFoundException;
    public ObservableList<String> getProgramListForCombo() throws SQLException, ClassNotFoundException;
    public List<ResponseIntakeDto> fetchIntakeByName(String name) throws SQLException, ClassNotFoundException;
}
