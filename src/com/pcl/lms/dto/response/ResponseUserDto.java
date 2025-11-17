package com.pcl.lms.dto.response;

public class ResponseUserDto {
    private String email;
    private String full_name;
    private int statusCode;
    private String message;

    public ResponseUserDto() {
    }

    public ResponseUserDto(String email, String full_name, int statusCode, String message) {
        this.email = email;
        this.full_name = full_name;
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
