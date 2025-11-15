package com.pcl.lms.env;

public class Session {
    private static String email;


    public static String getEmail() {
        return Session.email;
    }

    public static void setEmail(String email) {
      Session. email = email;
    }
    public static void clear() {
        email=null;
    }
}
