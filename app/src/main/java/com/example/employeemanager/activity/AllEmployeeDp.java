package com.example.employeemanager.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeemanager.Adapter.EmplAdapter;
import com.example.employeemanager.R;
import com.example.employeemanager.database.Mydatabase;
import com.example.employeemanager.model.Department;
import com.example.employeemanager.model.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AllEmployeeDp extends AppCompatActivity {
    ListView lv_emp;
    TextView tv_department;
    ArrayList<Employee> employees;
    ArrayList<Department> arrdepartments;
    EmplAdapter adapter;
    Mydatabase mydatabase;
    String dp_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_employee_dp);
        tv_department = findViewById(R.id.tv_dp_name);
        lv_emp = findViewById(R.id.lv_employee);
        mydatabase = new Mydatabase(this);
        arrdepartments = mydatabase.getAllDepartment();
        //
        Intent intent = getIntent();
        dp_name = intent.getStringExtra("department_name");
        employees = mydatabase.getEmployeeByDepartment(dp_name);
        Log.d("TAG", "onCreate: employee dp size "+employees.size());
        tv_department.setText(dp_name);
        adapter = new EmplAdapter(employees, this, R.layout.item_empl);
        lv_emp.setAdapter(adapter);
        lv_emp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllEmployeeDp.this, Detail.class);
                intent.putExtra("e_username",employees.get(position).getUsername());
                startActivity(intent);
            }
        });
        lv_emp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Employee employee = employees.get(position);
                Log.d("TAG", "onItemLongClick: position"+position);
                CharSequence[] items = {"Sửa thông tin", "Chuyển phòng", "Nghỉ việc"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(AllEmployeeDp.this);
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //sửa
//                            Toast.makeText(MainActivity.this, "Sửa sách", Toast.LENGTH_SHORT).show();
                            showDialogEdit(AllEmployeeDp.this, employee);
                        } else if (which == 1) {
                            //xoá
                            showDialogChangDp(AllEmployeeDp.this,employee);
                        } else {
                            showDialogQuitJob(employee.getUsername());
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    private void showDialogEdit(Activity activity, Employee employee) {
        final Employee e = employee;
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.edit_employee);
        final EditText edt_fullname, edt_dob, edt_countries, edt_major, edt_degree, edt_joinDate, edt_quitDate, edt_department;
        Button btn_cancel, btn_edit;
        edt_countries = dialog.findViewById(R.id.edt_add_place);
        edt_degree = dialog.findViewById(R.id.edt_add_degree);
        edt_dob = dialog.findViewById(R.id.edt_add_born);
        edt_fullname = dialog.findViewById(R.id.edt_add_name);
        edt_major = dialog.findViewById(R.id.edt_add_major);
        edt_joinDate = dialog.findViewById(R.id.edt_joindate);
        edt_quitDate = dialog.findViewById(R.id.edt_quitDate);
        edt_department = dialog.findViewById(R.id.edt_add_department);
        btn_cancel = dialog.findViewById(R.id.btn_cancle);
        btn_edit = dialog.findViewById(R.id.btn_edit);
        //hiển thị thông tin trước đó
        edt_fullname.setText(e.getFullname());
        edt_countries.setText(e.getCountries());
        edt_dob.setText(e.getDob());
        edt_degree.setText(e.getDegree());
        edt_department.setText(e.getDepartment().getD_name());
        edt_joinDate.setText(e.getJoinDate());
        edt_major.setText(e.getMajor());
        edt_quitDate.setText(e.getQuitDate());
        //
        edt_joinDate.setEnabled(false);
        edt_quitDate.setEnabled(false);
        edt_department.setEnabled(false);
        //
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.9);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = edt_fullname.getText().toString();
                String countries = edt_countries.getText().toString();
                String dob = edt_dob.getText().toString();
                String major = edt_major.getText().toString();
                String degree = edt_degree.getText().toString();
                if(fullname.isEmpty()||countries.isEmpty()||degree.isEmpty()||dob.isEmpty()||major.isEmpty()){
                    Toast.makeText(AllEmployeeDp.this,"Điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else{
                    e.setFullname(fullname);
                    e.setCountries(countries);
                    e.setDegree(degree);
                    e.setDob(dob);
                    e.setMajor(major);
                    if(mydatabase.updateEmployee(e)){
                        adapter.notifyDataSetChanged();
                        Toast.makeText(AllEmployeeDp.this,"Sửa thông tin thành công",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        refreshList();
                    }
                }

            }
        });
    }

    private void showDialogChangDp(Activity activity,Employee employee) {
        final Employee e = employee;
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.changdp_employee);
        TextView tv_current_dp = dialog.findViewById(R.id.tv_department);
        final Spinner spinner = dialog.findViewById(R.id.spinner_department);
        ArrayList<String> departments = new ArrayList<>();
        for(Department d:arrdepartments){
            departments.add(d.getD_name());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,departments);
        spinner.setAdapter(adapter);
        Button btn_save = dialog.findViewById(R.id.btn_save);
        tv_current_dp.setText(e.getDepartment().getD_name());
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String change_dp = spinner.getSelectedItem().toString();
               Department department1 = mydatabase.findDepartmentByName(change_dp);
                e.setDepartment(department1);
                if(mydatabase.updateEmployee(e)){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(AllEmployeeDp.this,"Chuyển phòng thành công",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    refreshList();
                }
            }
        });
    }
    public void refreshList() {
//        books.clear();
        employees = mydatabase.getEmployeeByDepartment(dp_name);
        adapter = new EmplAdapter(employees, this, R.layout.item_empl);
        lv_emp.setAdapter(adapter);
    }
    private void showDialogQuitJob(final String username) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(AllEmployeeDp.this);

        dialogDelete.setTitle("Chú ý!!");
        dialogDelete.setMessage("Bạn muốn cho nhân viên này nghỉ việc?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Employee employee = mydatabase.findEmployeeByUserName(username);
                employee.setIsworking(0);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                employee.setQuitDate(formattedDate);
                mydatabase.updateEmployee(employee);
                Toast.makeText(AllEmployeeDp.this,"Đã cho nhân viên nghỉ việc",Toast.LENGTH_SHORT).show();
            }
        });

        dialogDelete.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }
}