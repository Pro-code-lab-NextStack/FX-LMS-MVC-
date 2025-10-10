package com.pcl.lms.env;

public class StaticResource {
    private final static String VERSION = "1.0.0";
    private final static String COMPANY="Pro Code Lab";

    public static String getVERSION() {
        return VERSION;
    }
    public static String getCOMPANY() {
        return COMPANY;
    }
}
