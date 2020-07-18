package com.example.employeemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.employeemanager.R;
import com.example.employeemanager.model.Employee;

import java.util.ArrayList;

public class EmplAdapter extends BaseAdapter {
    private ArrayList<Employee> employees;
    Context context;
    private int resource;

    public EmplAdapter(ArrayList<Employee> employees, Context context, int resource) {
        this.employees = employees;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_fullname = convertView.findViewById(R.id.tv_fullname);
            viewHolder.img_stt = convertView.findViewById(R.id.img_stt);
            viewHolder.tv_dob = convertView.findViewById(R.id.tv_dob);
            viewHolder.tv_major = convertView.findViewById(R.id.tv_major);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Employee employee = employees.get(position);
        viewHolder.tv_fullname.setText(employee.getFullname());
        viewHolder.tv_dob.setText(employee.getDob());
        viewHolder.tv_major.setText(employee.getMajor());
        viewHolder.img_stt.setText(""+position+1);
        return convertView;
    }

    public class ViewHolder {
        TextView img_stt;
        TextView tv_fullname, tv_major, tv_dob;
    }
}
