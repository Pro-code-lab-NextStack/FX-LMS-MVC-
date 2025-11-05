package com.pcl.lms.env;

public class Session {
    private static String email;


    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        email = email;
    }
    public static void clear() {
        email=null;
    }
}
