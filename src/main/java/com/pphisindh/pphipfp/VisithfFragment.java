package com.pphisindh.pphipfp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pphisindh.pphipfp.Beans.DistrictBean;
import com.pphisindh.pphipfp.Beans.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisithfFragment extends Fragment implements View.OnClickListener{


    Spinner ddlDistricts;
    Spinner ddlHealthFacilities;
    Spinner ddlEmployees;

    DistrictBean districtBean;
    DistrictBean hfBeans;
    SQLiteController controller;
    EditText etVisitDate;

    Button btnNext;
    Button btnCancel;
    TextView tvMsg;
    String msg;

    public VisithfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visithf, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{
            controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);

            btnNext=(Button)getActivity().findViewById(R.id.btnNext);
            btnCancel=(Button)getActivity().findViewById(R.id.btnCancel);
            tvMsg=(TextView)getActivity().findViewById(R.id.tvMsg);

            btnNext.setOnClickListener(this);
            btnCancel.setOnClickListener(this);


            ddlDistricts=(Spinner)getActivity().findViewById(R.id.ddlDistricts);
            ddlHealthFacilities=(Spinner)getActivity().findViewById(R.id.ddlHealthFacilities);
            ddlEmployees=(Spinner)getActivity().findViewById(R.id.ddlEmployees);


            ArrayAdapter<DistrictBean> dataAdapter = new ArrayAdapter<DistrictBean>(getActivity(), android.R.layout.simple_spinner_item, controller.getDistricts());
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ddlDistricts.setAdapter(dataAdapter);
            ddlDistricts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        districtBean=(DistrictBean) ddlDistricts.getSelectedItem();
                        hfBeans=null;

                        ArrayAdapter<DistrictBean> hfAdapter = new ArrayAdapter<DistrictBean>(getActivity(), android.R.layout.simple_spinner_item, controller.getHealthFacilities(districtBean.getHF_ID()));
                        hfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ddlHealthFacilities.setAdapter(hfAdapter);

                    }catch ( Exception ex){
                        Toast.makeText(getActivity(),"Error:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            ddlHealthFacilities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        hfBeans=(DistrictBean) ddlHealthFacilities.getSelectedItem();

                        //getEmployees(String fkhfid,String hfid,String qtrid)
                        ArrayList<Employee> empList=controller.getEmployees(hfBeans.getHF_ID()+"",districtBean.getHF_ID()+"",controller.getQTRID()+"",getActivity());
                        Toast.makeText(getActivity(),"Employees:"+empList.size(),Toast.LENGTH_SHORT).show();
                        ArrayAdapter<Employee> employeeArrayAdapter = new ArrayAdapter<Employee>(getActivity(), android.R.layout.simple_spinner_item,empList);
                        employeeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ddlEmployees.setAdapter(employeeArrayAdapter);

                    }catch ( Exception ex){
                        Toast.makeText(getActivity(),"Error:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if(MasterVisitCls.isSuccess!=null && MasterVisitCls.isSuccess!=""){
                tvMsg.setText(""+ MasterVisitCls.isSuccess);
                //MasterVisitCls.isSuccess=null;
            }



            etVisitDate=(EditText)getActivity().findViewById(R.id.etVisitDate);
            final Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String rDate = df.format(c.getTime());
            etVisitDate.setText(""+rDate);
            etVisitDate.setEnabled(false);




        }catch (Exception ex){

        }
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnNext:{
                    if(districtBean!=null && hfBeans!=null){


                        MasterVisitCls.startTime =etVisitDate.getText()+"";
                        MasterVisitCls.phfId=districtBean.getHF_ID();
                        MasterVisitCls.hfId=hfBeans.getHF_ID();
                        MasterVisitCls.isActive=1;
                        MasterVisitCls.mo_empCode=((Employee)ddlEmployees.getSelectedItem()).getEMP_CODE();
                        MasterVisitCls.empId=controller.getEmpId();
                        MasterVisitCls.visitDate=""+etVisitDate.getText();
                        MasterVisitCls.code=controller.getCode(MasterVisitCls.hfId, MasterVisitCls.phfId, MasterVisitCls.empId);
                        tvMsg.setText("Code:"+controller.getCode(MasterVisitCls.hfId, MasterVisitCls.phfId, MasterVisitCls.empId));
                        LocationsCls.chkList=0;
                        LocationsCls.questionAns=null;
                        MasterVisitCls.isSuccess=null;
                         LocationsCls.attendanceBeanArrayList=new ArrayList<>();
                        FillVisitFromFragment fragment=new FillVisitFromFragment();
                        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_main,fragment);
                        fragmentTransaction.commit();

                    }else{
                        msg="Fields are Missing....!";
                        tvMsg.setText(msg);
                    }
                    break;
                }
                case R.id.btnCancel:{
                    getActivity().finish();
                    break;
                }
            }
        }catch (Exception ex){
            tvMsg.setText("Error in Register Visit:"+ex.getMessage());
        }



    }


}
