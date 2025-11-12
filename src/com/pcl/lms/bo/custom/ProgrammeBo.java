package com.pcl.lms.bo.custom;

import com.pcl.lms.bo.SuperBo;
import com.pcl.lms.dto.request.RequestProgrameDto;

import java.sql.SQLException;

public interface ProgrammeBo extends SuperBo {
    public boolean saveProgram(RequestProgrameDto program) throws SQLException, ClassNotFoundException;
    public String splitId(String comboText);
}
