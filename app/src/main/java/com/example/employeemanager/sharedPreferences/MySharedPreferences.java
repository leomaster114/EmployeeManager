package com.example.employeemanager.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.PhantomReference;

public class MySharedPreferences {
    private static final String SHAREDPREF_NAME = "EmplManager";
    private static MySharedPreferences mInstance;
    private Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public static synchronized MySharedPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySharedPreferences(context);
        }
        return mInstance;
    }

    public void saveUser(int id,String username, String password, String role) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Id",id);
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putString("role",role);
        editor.apply();
    }
    public String getUserName(){
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return  preferences.getString("username","-1");
    }
    public String getRole(){
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return  preferences.getString("role","-1");
    }
    public int getId(){
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return  preferences.getInt("Id",-1);
    }
    public boolean isLogined(){
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return  (preferences.getInt("Id",-1)!=-1);
    }
    public void clear(){
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
       editor.clear();
        editor.apply();
    }
}
