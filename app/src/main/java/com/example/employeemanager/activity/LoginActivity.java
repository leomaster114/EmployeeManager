package com.example.employeemanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeemanager.R;
import com.example.employeemanager.database.Mydatabase;
import com.example.employeemanager.model.Admin;
import com.example.employeemanager.model.Employee;
import com.example.employeemanager.sharedPreferences.MySharedPreferences;

public class LoginActivity extends AppCompatActivity {
    EditText edt_username, edt_pass;
    Button btn_login;
    TextView tv_register;
    Mydatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_pass = findViewById(R.id.edt_password);
        edt_username = findViewById(R.id.edt_username);
        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_no_account);
        mydatabase = new Mydatabase(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString().trim();
                String pass = edt_pass.getText().toString().trim();

                if (username.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // get from database;
                    Admin admin = mydatabase.findAdminByUserName(username);
                    Employee employee = mydatabase.findEmployeeByUserName(username);
                    if (admin == null && employee == null) {
                        Toast.makeText(LoginActivity.this, "Tai khoan khong ton tai", Toast.LENGTH_SHORT).show();
                    } else if (admin != null) {
                        Log.d("TAG", "onClick: " + admin.getPassword() + "," + pass);
                        if (admin.getPassword().equals(pass)) {
                            //save into sharedpreferences
                            MySharedPreferences preferences = MySharedPreferences.getInstance(LoginActivity.this);
                            preferences.saveUser(admin.getId(), admin.getUsername(), admin.getPassword(), "admin");

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Sai password", Toast.LENGTH_SHORT).show();
                        }
                    } else if (employee != null) {
                        Log.d("TAG", "onClick: " + employee.getUsername() + "," + employee.getPassword() + "," + pass);
                        if (employee.getPassword().equals(pass) && employee.getIsworking() == 1) {
                            MySharedPreferences preferences = MySharedPreferences.getInstance(LoginActivity.this);
                            preferences.saveUser(employee.getId(), employee.getUsername(), employee.getPassword(), "Nhân viên");

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else if (!employee.getPassword().equals(pass)) {
                            Toast.makeText(LoginActivity.this, "Sai password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Đã nghỉ việc", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        MySharedPreferences preferences = MySharedPreferences.getInstance(this);
        if (preferences.isLogined()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}