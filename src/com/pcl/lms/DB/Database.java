package com.pcl.lms.DB;

import com.pcl.lms.model.Student;
import com.pcl.lms.model.User;
import com.pcl.lms.utill.security.PasswordManager;

import java.util.ArrayList;

public class Database {
    public static ArrayList<User> userTable=new ArrayList<>();
    public static ArrayList <Student> studentTable=new ArrayList<>();
   static {
        userTable.add(new User(new PasswordManager().encode("1234"),27,"darkcreationsl98@gmail.com","gihan viraj"));
    }
}
