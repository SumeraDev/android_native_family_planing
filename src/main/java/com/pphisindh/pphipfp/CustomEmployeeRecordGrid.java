package com.pphisindh.pphipfp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 8/27/2017.
 */

public class CustomEmployeeRecordGrid extends BaseAdapter {
    private Context mContext;
    private final String[] empId;
    private final String[] empName;
    private final String[] empcode;
    private final String[] emp_desig;

    public CustomEmployeeRecordGrid(Context c,String[] empId,String[] empName,String[] empcode,String[] emp_desig) {        mContext = c;
        this.empId = empId;
        this.empName = empName;
        this.empcode=empcode;
        this.emp_desig=emp_desig;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return empcode.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid=null;
        try {



            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.grid_emp_record, null);


            } else {
                grid = (View) convertView;
            }

            CheckBox chkEmp = (CheckBox) grid.findViewById(R.id.chkEmp);
            //EditText etEmpName = (EditText) grid.findViewById(R.id.etEmpName);
            EditText etempCode = (EditText) grid.findViewById(R.id.etempCode);


            //etempCode.setBackgroundColor(Color.rgb(9, 79, 7));
            //etempCode.setTextColor(Color.WHITE);
            //etempCode.setTextSize(20.0f);
            etempCode.setGravity(Gravity.CENTER);

            chkEmp.setText(empName[position]+"\n("+emp_desig[position]+")");
            //etEmpName.setText(empName[position]);
            etempCode.setText(empcode[position]);


            //grid.setBackgroundResource(R.drawable.bordertable);
            grid.setPadding(30, 0, 30, 30);

        }catch (Exception ex){
            Toast.makeText(mContext,"Error:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return grid;

    }
}
