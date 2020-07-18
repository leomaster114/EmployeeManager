package com.example.employeemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.employeemanager.model.Admin;
import com.example.employeemanager.model.Department;
import com.example.employeemanager.model.Employee;
import com.example.employeemanager.model.Thongke;

import java.sql.Date;
import java.util.ArrayList;

public class Mydatabase extends SQLiteOpenHelper {
    private static final String TAG = "Mydatabase";
    private static String DBNAME = "EmployeeManager.db";
    private String id = "id";
    private String userName = "username";
    private String password = "password";
    private String role = "role";
    private String employee_table = "employees";
    private String fullname = "fullname";
    private String countries = "countries";
    private String degree = "degree";
    private String major = "major";
    private String dob = "dateOfbirth";
    private String joinDate = "joinDate";
    private String quitDate = "quitDate";
    private String dpId = "dp_id";
    private String isworking = "isworking";
    //
    private String admin_table = "admin";
    private String department_table = "departments";
    private String dp_name = "dp_name";
    private Context context;

    public Mydatabase(Context context) {
        super(context, DBNAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createEmployeeTable = String.format("Create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT ,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s TEXT,%s INTEGER,%s INTEGER,FOREIGN KEY (%s) REFERENCES %s(%s))",
                employee_table,id,userName,password,fullname,countries,degree,major,role,dob,joinDate,quitDate,dpId,isworking,dpId,department_table,id);
        String createAdminTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT,%s TEXT)",admin_table,id,fullname,userName,password);
        String createDepartmentTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT)",department_table,id,dp_name);
        db.execSQL(createDepartmentTable);
        db.execSQL(createAdminTable);
        db.execSQL(createEmployeeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropEmployeeTable = String.format("Drop table if exist %s",employee_table);
        String dropAdminTable = String.format("Drop table if exist %s",admin_table);
        String dropDepartmentTable = String.format("Drop table if exist %s",department_table);
        db.execSQL(dropEmployeeTable);
        db.execSQL(dropAdminTable);
        onCreate(db);
    }
    public void addAdmin(Admin admin){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(fullname,admin.getFullname());
        values.put(userName,admin.getUsername());
        values.put(password,admin.getPassword());
        database.insert(admin_table,null,values);
        database.close();
    }
    public Admin findAdminByUserName(String username){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM "+admin_table+" where username = ?";
        Cursor cursor =database.rawQuery(sql,new String[]{username});
        if(cursor.moveToFirst()){
            do {
                Admin admin = new Admin();
                admin.setFullname(cursor.getString(1));
                admin.setUsername(cursor.getString(2));
                admin.setPassword(cursor.getString(3));
                return admin;
            }while (cursor.moveToNext());
        }
        return null;
    }
    public void addEmployee(Employee e){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userName, e.getUsername());
        values.put(password, e.getPassword());
        values.put(fullname, e.getFullname());
        values.put(countries, e.getCountries());
        values.put(degree, e.getDegree());
        values.put(major,e.getMajor());
        values.put(role,e.getRole());
        values.put(dob,e.getDob());
        values.put(joinDate,e.getJoinDate());
        values.put(quitDate,e.getQuitDate());
        values.put(dpId,e.getDepartment().getId());
        values.put(isworking,1);
        database.insert(employee_table,null,values);
        Log.d(TAG, "addEmployee: "+e.getFullname());
        database.close();
    }
    public Employee findEmployeeByUserName(String userName){
        SQLiteDatabase database = getReadableDatabase();
     String sql = "SELECT e.id, e.username, e.password, e.fullname,e.countries,e.degree, e.major, e.role, e.dateOfbirth,e.joinDate, e.quitdate, e.dp_id,e.isworking, dp.id ,dp.dp_name FROM employees as e,departments as dp "
             +"WHERE e.dp_id = dp.id and e.username = ?";
     Cursor cursor = database.rawQuery(sql,new String[]{userName});
        if(cursor.moveToFirst()){
            do {
                Employee employee = new Employee();
                employee.setId(cursor.getInt(0));
                employee.setUsername(cursor.getString(1));
                employee.setPassword(cursor.getString(2));
                employee.setFullname(cursor.getString(3));
                employee.setCountries(cursor.getString(4));

                employee.setDegree(cursor.getString(5));
                employee.setMajor(cursor.getString(6));

                employee.setRole(cursor.getString(7));
                employee.setDob(cursor.getString(8));
                employee.setJoinDate(cursor.getString(9));
                employee.setQuitDate(cursor.getString(10));
                employee.setIsworking(cursor.getInt(12));
                Department dp = new Department();
                dp.setId(cursor.getInt(13));
                dp.setD_name(cursor.getString(14));
                employee.setDepartment(dp);
                return employee;
            }while (cursor.moveToNext());
        }
        return null;
    }
    public boolean updateEmployee(Employee employee) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
       values.put(userName, employee.getUsername());
       values.put(password, employee.getPassword());
       values.put(role, employee.getRole());
       values.put(fullname, employee.getFullname());
       values.put(dob, employee.getDob());
       values.put(countries, employee.getCountries());
       values.put(degree, employee.getDegree());
       values.put(major, employee.getMajor());
       values.put(joinDate,employee.getJoinDate());
       values.put(quitDate,employee.getQuitDate());
       values.put(dpId, employee.getDepartment().getId());
       values.put(isworking,employee.getIsworking());
       values.put(dpId,employee.getDepartment().getId());
        return database.update(employee_table, values, id + "=" + employee.getId(), null) > 0;
    }
    public ArrayList<Employee> getEmployeeByDepartment(String department){
        ArrayList<Employee> employees = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT e.id, e.username, e.password, e.fullname,e.countries,e.degree, e.major, e.role, e.dateOfbirth,e.joinDate, e.quitdate, e.dp_id,e.isworking, dp.id ,dp.dp_name FROM employees as e,departments as dp "
                +"WHERE e.dp_id = dp.id and dp.dp_name = ?";
        Cursor cursor = database.rawQuery(sql,new String[]{department});
        if(cursor.moveToFirst()){
            do {
                Employee employee = new Employee();
                employee.setId(cursor.getInt(0));
                employee.setUsername(cursor.getString(1));
                employee.setPassword(cursor.getString(2));
                employee.setFullname(cursor.getString(3));
                employee.setCountries(cursor.getString(4));

                employee.setDegree(cursor.getString(5));
                employee.setMajor(cursor.getString(6));

                employee.setRole(cursor.getString(7));
                employee.setDob(cursor.getString(8));
                employee.setJoinDate(cursor.getString(9));
                employee.setQuitDate(cursor.getString(10));
                employee.setIsworking(cursor.getInt(12));

                Department dp = new Department();
                dp.setId(cursor.getInt(13));
                dp.setD_name(cursor.getString(14));
                employee.setDepartment(dp);
                employees.add(employee);
            }while (cursor.moveToNext());
        }
        return employees;
    }
    public ArrayList<Thongke> findAllEmployeeInMajor(){
        ArrayList<Thongke> Thongkes = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT e.major,count(e.id)  FROM employees as e "
//                +"WHERE e.isworking = ?"
                +"GROUP BY major ORDER BY major";
        Cursor cursor = database.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do {
                Thongke tk = new Thongke();
                tk.setKey(cursor.getString(0));
                tk.setValue(cursor.getInt(1));
            }while (cursor.moveToNext());
        }
        return Thongkes;
    }

    public void addDepartment(Department dp) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dp_name, dp.getD_name());
        database.insert(department_table, null, contentValues);
        Log.d(TAG, "addDepartment: "+dp.getD_name());
        database.close();
    }
    public Department findDepartmentByName(String name){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM "+department_table+" WHERE "+dp_name +" =?";
//        String sql = "SELECT * FROM "+Type_table+" WHERE "+Type_name +" LIKE '%?%'";

        Cursor cursor = database.rawQuery(sql,new String[]{name});
        if(cursor.moveToFirst()){
            do{
                Department t = new Department();
                t.setId(cursor.getInt(0));
                t.setD_name(cursor.getString(1));
                return t;
            }while (cursor.moveToNext());
        }
        return null;
    }
    public Department findDepartmentById(int id){
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM "+department_table+" WHERE "+dpId +" =?";

        Cursor cursor = database.rawQuery(sql,new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            do{
                Department t = new Department();
                t.setId(cursor.getInt(0));
                t.setD_name(cursor.getString(1));
                return t;
            }while (cursor.moveToNext());
        }
        return null;
    }
    public ArrayList<Department> getAllDepartment(){
        ArrayList<Department> Departments = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM "+department_table;
        Cursor cursor = database.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                Department t = new Department();
                t.setId(cursor.getInt(0));
                t.setD_name(cursor.getString(1));
                Departments.add(t);
            }while (cursor.moveToNext());
        }
        return Departments;
    }

    public void clearAllDepartment() {
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("Delete from %s", department_table+" where id > 0");
        db.execSQL(query);
    }


}
