package com.example.employeemanager.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeemanager.R;
import com.example.employeemanager.database.Mydatabase;
import com.example.employeemanager.model.Admin;
import com.example.employeemanager.model.Department;
import com.example.employeemanager.model.Employee;
import com.example.employeemanager.sharedPreferences.MySharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    ArrayList<Department> departments;
    ArrayList<String> departments_name;
    ListView lv_department,lv_thongke;
    TextView tv_department,tv_personInfo,tv_logout,tv_thongke, tv_add_employee,tv_role, tv_name,tv_fullname, tv_dob,tv_countries,tv_degree,tv_major;
    TextView tv_username, tv_pass,tv_joinDate, tv_quitDate,tv_isworking;
    MySharedPreferences instance;
    String username,role;
    int id;
    Mydatabase mydatabase;
    LinearLayout ln_sn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = findViewById(R.id.drawlayout);
        lv_department = findViewById(R.id.list_department);
        lv_thongke = findViewById(R.id.list_thongke);
        tv_department =findViewById(R.id.tv_phongban);
        tv_personInfo = findViewById(R.id.tv_personInfo);
        tv_logout = findViewById(R.id.tv_logout);
        tv_thongke = findViewById(R.id.tv_thongke);
        tv_add_employee = findViewById(R.id.tv_addEmployee);
        tv_role = findViewById(R.id.tv_role);
        tv_name = findViewById(R.id.tv_name);
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
        ln_sn = findViewById(R.id.gift);
        instance = MySharedPreferences.getInstance(this);
        mydatabase = new Mydatabase(this);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_open);
        drawerLayout.addDrawerListener(drawerToggle);
        //them phong ban vao database
//        mydatabase.clearAllDepartment();
        String[] dp_name = new String[]{"Kinh doanh","Phần mềm","Kiểm định","Tổ chức","Tổng hợp"};
        for(int i = 0;i<dp_name.length;i++){
            Department dp = mydatabase.findDepartmentByName(dp_name[i]);
            Log.d("TAG", "onCreate: dp null"+(dp==null)+"dp_name"+dp_name[i]);
            if(dp==null) mydatabase.addDepartment(new Department(dp_name[i]));
        }
        departments_name = new ArrayList<>();
       departments = mydatabase.getAllDepartment();
        Log.d("TAG", "onCreate: departments size"+departments.size());
        for(Department d:departments){
            departments_name.add(d.getD_name());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,departments_name);
        lv_department.setAdapter(adapter);
        lv_department.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,AllEmployeeDp.class);
                intent.putExtra("department_name",departments.get(position).getD_name());
                startActivity(intent);
            }
        });
        //
        final ArrayList<String> arr_thongke = new ArrayList<>();
        arr_thongke.add("Theo độ tuổi");
        arr_thongke.add("Theo giới tính");
        arr_thongke.add("Theo chuyên môn");
        arr_thongke.add("Chuyên môn CNTT trên 3 năm");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,arr_thongke);
        lv_thongke.setAdapter(adapter1);
        lv_thongke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,ThongkeActivity.class);
                intent.putExtra("groupby",arr_thongke.get(position));
                startActivity(intent);
            }
        });

        tv_department.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
        tv_personInfo.setOnClickListener(this);
        tv_add_employee.setOnClickListener(this);
        tv_thongke.setOnClickListener(this);
        //getUser in sharedpreferences
        role = instance.getRole();
        username = instance.getUserName();
        if(role.equals("admin")){
            Admin admin = mydatabase.findAdminByUserName(username);
            if(admin!=null){
                tv_role.setText(role);
                tv_name.setText(admin.getFullname());
                tv_fullname.setText(admin.getFullname());
                tv_username.setText(admin.getUsername());
                tv_pass.setText(admin.getPassword());
                tv_degree.setText("Đại học");
                tv_countries.setText("Hà Nội");
                tv_dob.setText("15/12/1993");
                tv_major.setText("Quản lý nhân sự");
            }
        }else if(role.equals("Nhân viên")){
            tv_department.setVisibility(View.GONE);
            lv_department.setVisibility(View.GONE);
            tv_thongke.setVisibility(View.GONE);
            tv_add_employee.setVisibility(View.GONE);
            Employee employee = mydatabase.findEmployeeByUserName(username);
            if(employee !=null){
                tv_name.setText(employee.getFullname());
                tv_role.setText(role+"- Phòng "+ employee.getDepartment().getD_name());
                tv_fullname.setText(employee.getFullname());
                tv_username.setText(employee.getUsername());
                tv_pass.setText(employee.getPassword());
                tv_major.setText(employee.getMajor());
                tv_dob.setText(employee.getDob());
                tv_countries.setText(employee.getCountries());
                tv_degree.setText(employee.getDegree());
                tv_joinDate.setText(employee.getJoinDate());
                tv_quitDate.setText(employee.getQuitDate());
                if(employee.getIsworking()==1) tv_isworking.setText("Đang làm việc");
                else tv_isworking.setText("Đã nghỉ việc");
                // sinh nhật
                String dateofbirth = employee.getDob();
                String[]  dob = dateofbirth.split("/");
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                String[]  today = formattedDate.split("/");
                if(Integer.parseInt(dob[0])==Integer.parseInt(today[0])&&Integer.parseInt(dob[1])==Integer.parseInt(today[1])) ln_sn.setVisibility(View.VISIBLE);
                Log.d("Sinh nhat", "onCreate: to day"+today[0]+"/"+today[1]+", sn"+dob[0]+"/"+dob[1]);
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_phongban:{
                if(lv_department.getVisibility() == View.GONE){
                    lv_department.setVisibility(View.VISIBLE);
                }else{
                    lv_department.setVisibility(View.GONE);
                }
            }
            case R.id.tv_thongke:
                if(lv_thongke.getVisibility() == View.GONE){
                    lv_thongke.setVisibility(View.VISIBLE);
                }else{
                    lv_thongke.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_addEmployee:
                Intent intent = new Intent(MainActivity.this, AddPerson.class);
                startActivity(intent);
                break;
            case R.id.tv_personInfo:
                Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_logout:

                instance.clear();
                Intent intent2 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent2);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}