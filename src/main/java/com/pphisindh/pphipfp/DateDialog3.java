package com.pphisindh.pphipfp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 6/14/2017.
 */

public class DateDialog3 extends DialogFragment implements DatePickerDialog.OnDateSetListener  {

    EditText etbirthDate;
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final Calendar c = Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month= c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this,year, month, day);
    }
    public void etText(EditText etbirthDate){
        this.etbirthDate=etbirthDate;
    }

    public void onDateSet(DatePicker View, int year, int month, int day){

        try {
            //etbirthDate2 =(EditText)getActivity().findViewById(R.id.etbirthDate2);
            String date = year + "-" + (month + 1) + "-" + day;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            final Calendar dob = Calendar.getInstance();
            dob.setTime(sdf.parse(""+date));
            etbirthDate.setText(""+sdf.format(dob.getTime()));
        }catch (Exception ex){

        }


    }


}