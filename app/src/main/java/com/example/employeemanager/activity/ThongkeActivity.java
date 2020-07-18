package com.example.employeemanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.employeemanager.R;
import com.example.employeemanager.database.Mydatabase;
import com.example.employeemanager.model.Thongke;

import java.util.ArrayList;

public class ThongkeActivity extends AppCompatActivity {
    ListView lv_thongke;
    Mydatabase databases;
    ArrayList<Thongke> arrayList;
    ArrayList<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke);
        lv_thongke = findViewById(R.id.lv_thongke);
        Intent intent = getIntent();
        String groupby = intent.getStringExtra("groupby");
        databases = new Mydatabase(this);
        arrayList = databases.findAllEmployeeInMajor();
        Log.d("thongke", "onCreate: "+arrayList.size());

        arr = new ArrayList<>();
        for(Thongke tk:arrayList){
            arr.add(tk.getKey()+"-"+tk.getValue());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arr);
        lv_thongke.setAdapter(adapter);
    }
}