package com.example.employeemanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.employeemanager.R;
import com.example.employeemanager.database.Mydatabase;
import com.example.employeemanager.model.Department;
import com.example.employeemanager.model.Employee;

import java.util.ArrayList;

public class AddPerson extends AppCompatActivity {
    EditText tv_add_name, tv_add_dob, tv_pob, tv_degree,tv_major,edt_username, edt_password,edt_countries,edt_joindate, edt_quitdate;
    Spinner spinner;
    ArrayList<String> departments;
    Button btn_add, btn_cancel;
    Mydatabase mydatabase ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        tv_add_dob = findViewById(R.id.tv_add_born);
        tv_add_name = findViewById(R.id.tv_add_name);
        tv_degree = findViewById(R.id.tv_add_degree);
        tv_pob = findViewById(R.id.tv_add_place);
        tv_major = findViewById(R.id.tv_add_major);
        edt_countries = findViewById(R.id.tv_add_place);
        edt_username = findViewById(R.id.tv_add_username);
        edt_password = findViewById(R.id.tv_add_password);
        edt_joindate = findViewById(R.id.edt_joindate);
        edt_quitdate = findViewById(R.id.edt_quitDate);
        spinner = findViewById(R.id.spinner_add_department);
        btn_add = findViewById(R.id.btn_add);
        btn_cancel = findViewById(R.id.btn_cancle);
        mydatabase = new Mydatabase(this);

        departments = new ArrayList<>();
        departments.add("Kinh doanh");
        departments.add("Phần mềm");
        departments.add("Kiểm định");
        departments.add("Tổ chức");
        departments.add("Tổng hợp");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,departments);
        spinner.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, dob,pob,degree, major, department,username,pass,joindate, quitdate;
                name = tv_add_name.getText().toString().trim();
                dob = tv_add_dob.getText().toString().trim();
                pob = tv_pob.getText().toString().trim();
                degree = tv_degree.getText().toString().trim();
                major = tv_major.getText().toString().trim();
                username = edt_username.getText().toString().trim();
                pass = edt_password.getText().toString().trim();
                department = spinner.getSelectedItem().toString();
                joindate = edt_joindate.getText().toString().trim();
                quitdate = edt_quitdate.getText().toString().trim();
                if(username.equals("")||pass.equals("")||name.equals("")||dob.equals("")||pob.equals("")||degree.equals("")||major.equals("")||department.equals("")||joindate.equals("")){
                    Toast.makeText(AddPerson.this,"vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else{
                    Department department1 = mydatabase.findDepartmentByName(department);
                    Employee employee = new Employee(username,pass,name,pob,degree,major,"Nhân viên",dob,joindate,quitdate,department1,1);
                    mydatabase.addEmployee(employee);
                    Toast.makeText(AddPerson.this,"Thêm nhân viên thành công",Toast.LENGTH_SHORT).show();
                    edt_username.setText("");
                    edt_password.setText("");
                    tv_add_name.setText("");
                    tv_add_dob.setText("");
                    tv_degree.setText("");
                    tv_major.setText("");
                    tv_pob.setText("");
                    edt_joindate.setText("");
                    edt_quitdate.setText("");
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPerson.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}