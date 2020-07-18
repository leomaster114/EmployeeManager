package com.example.employeemanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeemanager.R;
import com.example.employeemanager.database.Mydatabase;
import com.example.employeemanager.model.Admin;

public class RegisterActivity extends AppCompatActivity {
    EditText edt_fullname, edt_username, edt_pass;
    Button btn_signUp;
    TextView tv_have_acc;
    Mydatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_fullname = findViewById(R.id.edt_fullname);
        edt_username = findViewById(R.id.edt_username);
        edt_pass = findViewById(R.id.edt_password);
        btn_signUp = findViewById(R.id.btn_signUp);
        tv_have_acc = findViewById(R.id.tv_account_exist);
        mydatabase = new Mydatabase(this);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = edt_fullname.getText().toString().trim();
                String username = edt_username.getText().toString().trim();
                String pass = edt_pass.getText().toString().trim();
                if(fullname.isEmpty()||username.isEmpty()||pass.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else{
                    mydatabase.addAdmin(new Admin(fullname,username,pass));
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        tv_have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}