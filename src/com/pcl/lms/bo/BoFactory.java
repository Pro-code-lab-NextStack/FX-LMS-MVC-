package com.pcl.lms.bo;

import com.pcl.lms.bo.custom.impl.*;
import com.pcl.lms.utill.BoType;

public class BoFactory {
    private static  BoFactory boFactory;
    private BoFactory(){}
    public static BoFactory getInstance(){
        if(boFactory==null){
            boFactory=new BoFactory();
        }
        return boFactory;
    }
    public <T>T getBo(BoType boType){
        switch(boType){
            case USER:
                return(T) new UserBoImpl();
            case STUDENT:
                return (T) new StudentBoImpl();
            case TEACHER:
                return (T) new TeacherBoImpl();
            case PROGRAME:
                return (T) new ProgrammeBoImpl();
             case INTAKE:
                 return (T) new IntakeBoImpl();
             case REGISTRATION:
                 return (T) new RegisterBoImpl();
                default:
                    return null;
        }
    }
}
