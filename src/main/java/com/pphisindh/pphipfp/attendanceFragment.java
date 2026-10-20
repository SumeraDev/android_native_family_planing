package com.pphisindh.pphipfp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.pphisindh.pphipfp.Beans.AttendanceBean;
import com.pphisindh.pphipfp.Beans.Employee;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class attendanceFragment extends Fragment implements View.OnClickListener{

    Button btnGo;

    String[] empcodes=new String[0];
    private  String[] empId;
    private  String[] empName;
    private  String[] empcode;
    private  String[] emp_desig;
    SQLiteController controller;

    ArrayList<Employee> employeeArrayList;

    GridView gridEmp;

    public attendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{
            controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
            btnGo=(Button)getActivity().findViewById(R.id.btnGo);
            gridEmp = (GridView) getActivity().findViewById(R.id.gridEmp);


            int chk = 0;
            int chkLength = 0;
            int count=0;
            employeeArrayList=controller.getEmployees(MasterVisitCls.hfId,getActivity());

            //Toast.makeText(getActivity(),"Records:"+MasterVisitCls.hfId+","+employeeArrayList.size(),Toast.LENGTH_SHORT).show();

            empId = new String[employeeArrayList.size()];
            empName = new String[employeeArrayList.size()];
            empcode = new String[employeeArrayList.size()];
            emp_desig= new String[employeeArrayList.size()];
            int height=0;


            for (Employee employee : employeeArrayList) {

                height=height+130;
                empId[count] = employee.getEMP_ID()+"";
                empName[count] = employee.getEMP_NAME()+"";
                empcode[count] = employee.getEMP_CODE()+"";
                emp_desig[count] = employee.getEmp_desig()+"";

                count++;
            }


            CustomEmployeeRecordGrid adapter = new CustomEmployeeRecordGrid(getActivity(), empId, empName, empcode,emp_desig);
            gridEmp = (GridView) getActivity().findViewById(R.id.gridEmp);
            gridEmp.setAdapter(adapter);
            ViewGroup.LayoutParams params = gridEmp.getLayoutParams();
            params.height = height;
            gridEmp.setLayoutParams(params);
            gridEmp.requestLayout();

            btnGo.setOnClickListener(this);





        }catch (Exception ex){

        }
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnGo: {

                    try{
                        //LocationsCls.attendanceBeanArrayList=new ArrayList<>();

                        empcodes=new String[gridEmp.getCount()];

                        int countsel=0;
                        boolean chksel=false;
                        int tempchkLength=0;
                        for(int i = 0; i < gridEmp.getCount(); i++) {

                            ViewGroup gridChild = (ViewGroup) gridEmp.getChildAt(i);
                            if(gridChild!=null){
                                for(int j = 0; j < gridChild.getChildCount(); j++){
                                    String str="";
                                    View viewItem = gridChild.getChildAt(j);
                                    if( gridChild.getChildAt(j) instanceof CheckBox) {

                                        if(((CheckBox) gridChild.getChildAt(j)).isChecked() && ((CheckBox) gridChild.getChildAt(j)).isEnabled()){

                                            str=((CheckBox) gridChild.getChildAt(j)).getText()+"";
                                            if(str!=null && str!=""){

                                                ((CheckBox) gridChild.getChildAt(j)).setChecked(true);
                                                ((CheckBox) gridChild.getChildAt(j)).setEnabled(true);
                                                chksel=true;

                                            }
                                            j++;
                                            String datestr=((EditText) gridChild.getChildAt(j)).getText()+"";

                                            empcodes[countsel]=datestr+"";
                                            //Toast.makeText(getActivity(),datestr+"",Toast.LENGTH_LONG).show();

                                            LocationsCls.attendanceBeanArrayList.add(new AttendanceBean(MasterVisitCls.code,7,4,"None",1,empcodes[countsel],1,0));

                                            if(str!=null){
                                                tempchkLength++;

                                            }
                                            countsel++;

                                        }

                                    }

                                }


                            }

                        }
                        Toast.makeText(getActivity(),LocationsCls.attendanceBeanArrayList.size()+"", Toast.LENGTH_LONG).show();
                        AddOtherAbsenteesFragment fragment=new AddOtherAbsenteesFragment();
                        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_main,fragment);
                        fragmentTransaction.commit();


                    }catch (Exception ex){

                    }

                    break;
                }
            }

        }catch (Exception ex){

        }
    }
}
