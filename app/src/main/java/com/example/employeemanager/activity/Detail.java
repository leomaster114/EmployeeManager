package com.example.employeemanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.employeemanager.R;
import com.example.employeemanager.database.Mydatabase;
import com.example.employeemanager.model.Employee;
import com.example.employeemanager.sharedPreferences.MySharedPreferences;

public class Detail extends AppCompatActivity {
    TextView tv_fullname, tv_dob,tv_countries,tv_degree,tv_major,tv_department;
    TextView tv_username, tv_pass,tv_joinDate, tv_quitDate,tv_isworking;
    Mydatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tv_fullname = findViewById(R.id.tv_fullname);
        tv_username = findViewById(R.id.tv_username);
        tv_pass = findViewById(R.id.tv_pass);
        tv_degree = findViewById(R.id.tv_degree);
        tv_dob = findViewById(R.id.tv_dob);
        tv_countries = findViewById(R.id.tv_countries);
        tv_major  = findViewById(R.id.tv_major);
        tv_joinDate = findViewById(R.id.tv_joindate);
        tv_quitDate = findViewById(R.id.tv_quitdate);
        tv_isworking = findViewById(R.id.tv_isworking);
        tv_department = findViewById(R.id.tv_department);
        mydatabase = new Mydatabase(this);
        Intent intent = getIntent();
        String username = intent.getStringExtra("e_username");
        Employee employee = mydatabase.findEmployeeByUserName(username);
        tv_fullname.setText(employee.getFullname());
        tv_department.setText(employee.getDepartment().getD_name());
        tv_countries.setText(employee.getCountries());
        tv_degree.setText(employee.getDegree());
        tv_dob.setText(employee.getDob());
        tv_major.setText(employee.getMajor());
        tv_joinDate.setText(employee.getJoinDate());
        tv_quitDate.setText(employee.getQuitDate());
        if(employee.getIsworking()==1) tv_isworking.setText("Đang làm việc");
        else tv_isworking.setText("Đã nghỉ việc");
        tv_pass.setText(employee.getPassword());
        tv_username.setText(employee.getUsername());
    }
}