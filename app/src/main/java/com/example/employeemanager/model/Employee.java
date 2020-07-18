package com.example.employeemanager.model;

import java.sql.Date;

public class Employee {
    private int id;
    private String username, password,fullname,countries, degree, major,role;
    private String dob, joinDate,quitDate;
    private Department department;
    private int isworking;
    public Employee() {

    }

    public Employee(String username, String password, String fullname, String countries, String degree, String major, String role, String dob, String joinDate, String quitDate, Department department,int isworking) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.countries = countries;
        this.degree = degree;
        this.major = major;
        this.role = role;
        this.dob = dob;
        this.joinDate = joinDate;
        this.quitDate = quitDate;
        this.department = department;
        this.isworking = isworking;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(String quitDate) {
        this.quitDate = quitDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getIsworking() {
        return isworking;
    }

    public void setIsworking(int isworking) {
        this.isworking = isworking;
    }
}
