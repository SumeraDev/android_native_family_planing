package com.pphisindh.pphipfp;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LENOVO on 05-Sep-18.
 */

public class EmpservingAdapter  extends BaseAdapter {
    private Context mContext;
    private String[] empservings;

    public EmpservingAdapter(Context c, String[] empservings) {
        mContext = c;
        this.empservings=empservings;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return empservings.length;
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
                    .getSystemService(LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.empservingdetail, null);


            } else {
                grid = (View) convertView;
            }

            TextView tvempserving = (TextView) grid.findViewById(R.id.tvempserving);


            tvempserving.setText(empservings[position]);



            grid.setBackgroundResource(R.drawable.bordertable);
            grid.setPadding(30, 0, 30, 30);
        }catch (Exception ex){
            Toast.makeText(mContext.getApplicationContext(),"Exception in grid:"+ex.getMessage()+","+ex.getStackTrace(),Toast.LENGTH_LONG).show();
        }
        return grid;

    }
}
