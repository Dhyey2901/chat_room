package com.chat.app.chatroomapp.dto;

//Data Transfer object
public class UserRegisteredDTO {


    private String name;


    private String email_id;

    private String password;

    private String branch;

    private String sem;

    private String clg_id;


    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getClg_id() {
        return clg_id;
    }

    public void setClg_id(String clg_id) {
        this.clg_id = clg_id;
    }

    String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
