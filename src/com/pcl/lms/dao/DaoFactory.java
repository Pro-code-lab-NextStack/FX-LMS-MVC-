package com.pcl.lms.dao;

import com.pcl.lms.dao.custom.impl.ProgramDaoImpl;
import com.pcl.lms.dao.custom.impl.StudentDaoImpl;
import com.pcl.lms.dao.custom.impl.TeacherDaoImpl;
import com.pcl.lms.dao.custom.impl.UserDaoImpl;
import com.pcl.lms.utill.DaoType;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory() {}
    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory=new DaoFactory();
        }
        return daoFactory;
    }
    public <T>T getDao(DaoType daoType) {
        switch (daoType) {
            case USER:
                return(T)  new UserDaoImpl();
            case STUDENT:
                return(T) new StudentDaoImpl();
            case TEACHER:
                return (T) new TeacherDaoImpl();
             case  PROGRAME:
                 return(T) new ProgramDaoImpl();
                default:
                    return  null;
        }
    }
}
