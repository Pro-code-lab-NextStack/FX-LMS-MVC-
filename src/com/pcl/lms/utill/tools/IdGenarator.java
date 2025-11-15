package com.pcl.lms.utill.tools;

public class IdGenarator {
    public  static String generateId(String lastID){
        if (lastID!=null){
           String [] split =lastID.split("-");
           String lastChar=split[1];
            int lastDigit = Integer.parseInt(lastChar);
            lastDigit++;
            return split[0]+"-"+lastDigit;
        }
        return null;

    }
}
