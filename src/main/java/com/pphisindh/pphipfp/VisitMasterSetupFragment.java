package com.pphisindh.pphipfp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pphisindh.pphipfp.Beans.Employee;
import com.pphisindh.pphipfp.Beans.Questions_category;
import com.pphisindh.pphipfp.Beans.Tbl_emp_serving;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisitMasterSetupFragment extends Fragment implements View.OnClickListener{

    Spinner ddlEmployees;
    EditText etempCode;
    EditText etempname;
    ArrayList<String> listsel=new ArrayList<>();
    LinearLayout layout_option2;
    SQLiteController controller;
    Button btnAdd;
    Button btnSubmit;
    TextView tvMsg;
    String msg;

    Employee employee;

    int len=0;
    String empservings[]=new String[0];
    ArrayList<Tbl_emp_serving> emp_servings=null;
    ArrayList<String> empservicelist=new ArrayList<>();
    ArrayList<String> conNamelist=new ArrayList<>();
    ArrayList<Questions_category> list;
    GridView grid;
    public VisitMasterSetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visit_master_setup, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        try{

            controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
            etempCode=(EditText) getActivity().findViewById(R.id.etempCode);
            etempname=(EditText) getActivity().findViewById(R.id.etempname);
            grid=(GridView)getActivity().findViewById(R.id.grid);

            btnAdd=(Button)getActivity().findViewById(R.id.btnAdd);
            btnSubmit=(Button)getActivity().findViewById(R.id.btnSubmit);
            tvMsg=(TextView)getActivity().findViewById(R.id.tvMsg);

            btnAdd.setOnClickListener(this);
            btnSubmit.setOnClickListener(this);

            emp_servings=new ArrayList<>();
            layout_option2=(LinearLayout)getActivity().findViewById(R.id.layout_option2);

            loadQuestioncat();


            ddlEmployees=(Spinner)getActivity().findViewById(R.id.ddlEmployees);
            ArrayAdapter<Employee> dataAdapter = new ArrayAdapter<Employee>(getActivity(), android.R.layout.simple_spinner_item, controller.getEmployees(MasterVisitCls.hfId,getActivity()));
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ddlEmployees.setAdapter(dataAdapter);
            ddlEmployees.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        employee=(Employee) ddlEmployees.getSelectedItem();
                        etempCode.setText(""+employee.EMP_CODE);
                        etempname.setText(""+employee.getEMP_NAME());

                    }catch ( Exception ex){
                        Toast.makeText(getActivity(),"Error:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });





        }catch (Exception ex){

        }
    }

    public void loadQuestioncat(){
        try{
            listsel=new ArrayList<>();
            list=controller.getQuestioncat();
            layout_option2.removeAllViews();
            for(final Questions_category questions_category:list) {

                CheckBox chk = new CheckBox(getActivity());
                chk.setTextColor(Color.BLACK);
                chk.setId(Integer.parseInt(questions_category.getId() + ""));
                chk.setText("" + questions_category.getQues_cat());
                //empservicelist=new ArrayList<>();


                if (questions_category.getQues_cat().equalsIgnoreCase("OPD")) {
                    chk.setChecked(true);
                    chk.setEnabled(false);
                    listsel.add("" + questions_category.getId());
                    conNamelist.add("" + questions_category.getQues_cat());
                    questions_category.setIs_active("1"); // Mark OPD as active
                }

                chk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = ((CheckBox) v).isChecked();
                        String id = ((CheckBox) v).getId() + "";
                        String name = ((CheckBox) v).getText() + "";
                        try {
                            if (checked) {
                                listsel.add("" + id);
                                conNamelist.add("" + name);
                                int index = 0;
                                for(Questions_category cat:list){
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
                                        conNamelist.remove(index);
                                    }
                                    index++;
                                }*/
                                int index = 0;
                                for(Questions_category cat:list){
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



        }catch (Exception ex){

        }
    }
    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnAdd:{
                    try {
                        if (etempCode.getText() != null && etempCode.getText() + "" != ""
                                && etempname.getText() != null && etempname.getText() + "" != "") {
                            String empservingstemp = "" + etempname.getText();
                            int index = 0;
                            //for(String empser:listsel) {
                            for (Questions_category cat : list) {
                                if (cat.getIs_active().equals("1")) {
                                    emp_servings.add(new Tbl_emp_serving("0", cat.getId(), MasterVisitCls.hfId + "", MasterVisitCls.phfId + "", "" + controller.getQTRID(), "0", etempCode.getText() + "", etempname.getText() + "", "2"));
                                    empservingstemp = empservingstemp + "," + cat.getQues_cat();

                                }
                                index++;

                            }
                            loadQuestioncat();
                            empservicelist.add(empservingstemp);
                            empservings = new String[empservicelist.size()];
                            len = 0;
                            int height = 0;
                            for (String ser : empservicelist) {
                                height = height + 130;
                                empservings[len] = ser;
                                len++;
                            }
                            grid = (GridView) getActivity().findViewById(R.id.grid);
                            EmpservingAdapter adapter = new EmpservingAdapter(getActivity(), empservings);
                            grid = (GridView) getActivity().findViewById(R.id.grid);
                            grid.setAdapter(adapter);
                            ViewGroup.LayoutParams params = grid.getLayoutParams();
                            params.height = height;
                            grid.setLayoutParams(params);
                            grid.requestLayout();


                            Toast.makeText(getActivity(), "Emp:" + etempname.getText(), Toast.LENGTH_LONG).show();
                            etempname.setText("");
                            etempCode.setText("");
                        } else {
                            msg = "Fields are Missing....!";
                            //tvMsg.setText(msg);
                        }

                    }catch (Exception ex){
                        Toast.makeText(getActivity(),"Exception:"+ex.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    break;

                }
                case R.id.btnSubmit:{
                    try {
                        if (emp_servings.size() != 0) {
                            String msg = controller.insertempserving(emp_servings);
                            if (msg.equals("Inserted")) {
                                msg = controller.inserthfservices(MasterVisitCls.hfservices, MasterVisitCls.hfId + "", MasterVisitCls.phfId + "");
                                Toast.makeText(getActivity(), "Successfully " + msg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(this.getActivity(), VisithfActivity.class);
                                startActivity(i);
                                getActivity().finish();

                            }
                            Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception ex){
                        Toast.makeText(getActivity(), "Exception :" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                }
            }
        }catch (Exception ex){
            tvMsg.setText("Error in Register Visit:"+ex.getMessage());
        }



    }
}
