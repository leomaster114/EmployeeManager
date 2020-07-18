package com.example.employeemanager.model;

public class Department {
    private int id;
    private String d_name;

    public Department() {
    }

    public Department(String d_name) {
        this.d_name = d_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }
}
