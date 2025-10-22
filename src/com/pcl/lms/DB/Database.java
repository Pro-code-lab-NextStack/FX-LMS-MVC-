package com.pcl.lms.DB;

import com.pcl.lms.model.*;
import com.pcl.lms.utill.security.PasswordManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Database {
    public static ArrayList<User> userTable=new ArrayList<>();
    public static ArrayList <Student> studentTable=new ArrayList<>();
    public static ArrayList <Teacher> teacherTable=new ArrayList<>();
    public static ArrayList<Programme> programmeTable=new ArrayList<>();
    public static ArrayList<Intake> intakeTable=new ArrayList<>();
   static {
        userTable.add(new User(new PasswordManager().encode("1234"),27,"darkcreationsl98@gmail.com","gihan viraj"));
        teacherTable.add(new Teacher("Gihan","T-1","0705295574","Kandy"));
        teacherTable.add(new Teacher("Hasika","T-2","119","Panadura"));
    }
}
