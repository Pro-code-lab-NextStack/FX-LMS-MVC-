package com.pcl.lms.utill.security;

import org.mindrot.BCrypt;

public class PasswordManager {
    public String encode(String rowPassword){
       return BCrypt.hashpw(rowPassword,BCrypt.gensalt(10));
    }
}
