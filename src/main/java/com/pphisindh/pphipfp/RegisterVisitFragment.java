package com.pphisindh.pphipfp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pphisindh.pphipfp.Beans.DistrictBean;
import com.pphisindh.pphipfp.Beans.Tbl_hf_servicesnew;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterVisitFragment extends Fragment implements View.OnClickListener{


    Spinner ddlDistricts;
    Spinner ddlHealthFacilities;
    DistrictBean districtBean;
    DistrictBean hfBeans;
    SQLiteController controller;
    EditText etVisitDate;

    Button btnNext;
    Button btnCancel;
    TextView tvMsg;
    String msg;
    TextView tvQuarter;

    ArrayList<String> listsel=new ArrayList<>();
    ArrayList<Tbl_hf_servicesnew> list;

    LinearLayout layout_option2;
    public RegisterVisitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_visit, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{
            controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
            tvQuarter=(TextView) getActivity().findViewById(R.id.tvQuarter);
            tvQuarter.setText("Visit will be saved in quarter "+controller.getQuarter());

            btnNext=(Button)getActivity().findViewById(R.id.btnNext);
            btnCancel=(Button)getActivity().findViewById(R.id.btnCancel);
            tvMsg=(TextView)getActivity().findViewById(R.id.tvMsg);

            btnNext.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            layout_option2=(LinearLayout)getActivity().findViewById(R.id.layout_option2);



            ddlDistricts=(Spinner)getActivity().findViewById(R.id.ddlDistricts);
            ddlHealthFacilities=(Spinner)getActivity().findViewById(R.id.ddlHealthFacilities);


            ArrayAdapter<DistrictBean> dataAdapter = new ArrayAdapter<DistrictBean>(getActivity(), android.R.layout.simple_spinner_item, controller.getDistricts());
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ddlDistricts.setAdapter(dataAdapter);
            ddlDistricts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        districtBean=(DistrictBean) ddlDistricts.getSelectedItem();
                        hfBeans=null;

                        //Toast.makeText(getActivity(),"hfId:"+districtBean.getHF_PARENT_OFFICE(),Toast.LENGTH_SHORT).show();
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

            list=controller.getServices();

            layout_option2.removeAllViews();
            for(Tbl_hf_servicesnew servicesnew:list) {

                CheckBox chk = new CheckBox(getActivity());
                chk.setTextColor(Color.BLACK);
                chk.setId(Integer.parseInt(servicesnew.getId() + ""));
                chk.setText("" + servicesnew.getServicesName());

                chk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = ((CheckBox) v).isChecked();
                        String id = ((CheckBox) v).getId() + "";
                        try {
                            if (checked) {
                                //listsel.add("" + id);
                                int index = 0;
                                for(Tbl_hf_servicesnew cat:list){
                                    if(id.equals(""+cat.getId())){

                                        list.get(index).setIs_active("1");
                                        //Toast.makeText(getActivity(),""+cat.getQues_cat(),Toast.LENGTH_LONG).show();

                                    }
                                    index++;
                                }


                            } else {
                                /*int index = 0;
                                for (String selid : listsel) {
                                    if (selid.equals("" + id)) {
                                        listsel.remove(index);
                                    }
                                    index++;
                                }*/
                                int index = 0;
                                for(Tbl_hf_servicesnew cat:list){
                                    if(id.equals(""+cat.getId())){

                                        list.get(index).setIs_active("0");
                                        //Toast.makeText(getActivity(),""+cat.getQues_cat(),Toast.LENGTH_LONG).show();

                                    }
                                    index++;
                                }


                            }
                        }catch (Exception ex){
                            Toast.makeText(getActivity(),"Exception:"+ex.getMessage(),Toast.LENGTH_LONG).show();
                        }



                    }
                });

                layout_option2.addView(chk);
            }

            /*etVisitDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateDialog3 dialog = new DateDialog3();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    dialog.show(getActivity().getFragmentManager(), "datePicker");

                }
            } );*/




        }catch (Exception ex){

        }
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnNext:{
                    if(districtBean!=null && hfBeans!=null){
                        listsel=new ArrayList<>();
                        for(Tbl_hf_servicesnew s:list){
                            if(s.getIs_active().equals("1")){
                                listsel.add(""+s.getId());
                            }
                        }

                        MasterVisitCls.startTime =etVisitDate.getText()+"";
                        MasterVisitCls.phfId=districtBean.getHF_ID();
                        MasterVisitCls.hfId=hfBeans.getHF_ID();
                        MasterVisitCls.isActive=1;
                        MasterVisitCls.empId=controller.getEmpId();
                        MasterVisitCls.visitDate=""+etVisitDate.getText();
                        MasterVisitCls.code=controller.getCode(MasterVisitCls.hfId, MasterVisitCls.phfId, MasterVisitCls.empId);
                        Log.d("noman","noman"+controller.getCode(MasterVisitCls.hfId, MasterVisitCls.phfId, MasterVisitCls.empId));
                        tvMsg.setText("cCode:"+controller.getCode(MasterVisitCls.hfId, MasterVisitCls.phfId, MasterVisitCls.empId));
                        LocationsCls.chkList=0;
                        MasterVisitCls.isSuccess=null;


                        MasterVisitCls.hfservices=listsel;
                        LocationsCls.attendanceBeanArrayList=new ArrayList<>();

                        Toast.makeText(getActivity(),"Count:"+listsel.size(),Toast.LENGTH_LONG).show();

                        if(MasterVisitCls.code.split("-").length>=4) {
                            VisitMasterSetupFragment fragment = new VisitMasterSetupFragment();
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_main, fragment);
                            fragmentTransaction.commit();
                        }

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
