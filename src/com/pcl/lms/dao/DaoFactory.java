package com.pcl.lms.dao;

import com.pcl.lms.dao.cutom.UserDao;
import com.pcl.lms.dao.cutom.impl.StudentDaoImpl;
import com.pcl.lms.dao.cutom.impl.UserDaoImpl;
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
                default:
                    return  null;
        }
    }
}
