package com.pphisindh.pphipfp;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pphisindh.pphipfp.Beans.Employee;
import com.pphisindh.pphipfp.Beans.OptionBean;
import com.pphisindh.pphipfp.Beans.Questionsbean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FillVisitFromFragment extends Fragment implements View.OnClickListener{

    TextView tvCat;
    TextView tvQuestion;
    TextView tvMsg;
    RadioGroup rbtn;
    Button btnNext;
    Button btnPrevious;
    Button btnCancel;
    Button btnSubmit;
    int buttons = 1;
    SQLiteController controller;
    ArrayList<Questionsbean> questionsbeanArrayList;
    ArrayList<OptionBean> optionBeanArrayList;
    OptionBean optionBean; //getOptions(int qId)
    Questionsbean questionsbean;
    int index=0;
    int optionId=0;
    int questionId=0;

    Answers answers;
    int ischecked=0;
    File imageFile;
    String imgName="";
    String imgsource="http://jobs.pphisindh.org/PPHIAndroidTest/epicardpics/";

    CheckBox chkhasempCode;
    EditText etempCode;
    EditText etisLate;
    int isHr=0;
    int isSono=0;

    public FillVisitFromFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fill_visit_from, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{
            controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
            String empdesId=controller.getDesignbyempid(MasterVisitCls.mo_empCode,getActivity());
            questionsbeanArrayList=controller.getQuestions(MasterVisitCls.mo_empCode,empdesId,getActivity());
            Toast.makeText(getActivity(),"questions:"+questionsbeanArrayList.size(),Toast.LENGTH_SHORT).show();


            if(LocationsCls.questionAns==null) {
                LocationsCls.questionAns = new ArrayList<>();
            }
            tvCat=(TextView)getActivity().findViewById(R.id.tvCat);
            tvQuestion=(TextView)getActivity().findViewById(R.id.tvQuestion);
            tvMsg=(TextView)getActivity().findViewById(R.id.tvMsg);
            btnNext=(Button)getActivity().findViewById(R.id.btnNext);
            btnPrevious=(Button)getActivity().findViewById(R.id.btnPrevious);
            btnCancel=(Button)getActivity().findViewById(R.id.btnCancel);
            btnSubmit=(Button)getActivity().findViewById(R.id.btnSubmit);

            btnNext.setOnClickListener(this);
            btnPrevious.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            btnSubmit.setOnClickListener(this);

            etempCode=(EditText)getActivity().findViewById(R.id.etempCode);
            etisLate=(EditText)getActivity().findViewById(R.id.etisLate);
            chkhasempCode=(CheckBox)getActivity().findViewById(R.id.chkhasempCode);
            chkhasempCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try{
                        if(chkhasempCode.isChecked()==true){
                            etempCode.setEnabled(true);
                        }else {
                            etempCode.setEnabled(false);
                            etempCode.setText("");
                        }
                    }catch (Exception ex){

                    }
                }
            });



            rbtn=(RadioGroup)getActivity().findViewById(R.id.rbtn);
            rbtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    try {
                        int a = rbtn.indexOfChild(getActivity().findViewById(checkedId));
                        optionId=getActivity().findViewById(checkedId).getId();
                        if(optionId==7){
                            etisLate.setVisibility(View.VISIBLE);
                        }else{
                            etisLate.setVisibility(View.GONE);
                            etisLate.setText("");
                        }

                        ischecked=1;
                    }catch (Exception ex){
                        //Toast.makeText(getActivity(),"Error:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
            if(LocationsCls.chkList==0) {
                setOptions(index);
                LocationsCls.chkList=1;
            }

        }catch (Exception ex){

        }
    }
    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnNext:{
                    etisLate.setVisibility(View.GONE);
                    tvMsg.setText("");
                    if(ischecked==0){
                        tvMsg.setText("Please select Option.......!");
                    }else{

                        int isPresent=1;
                        int isActive=0;
                        if(optionId==7){
                            isPresent=2;
                            isActive=1;
                            etisLate.setVisibility(View.VISIBLE);
                        }else if(optionId==5){
                            isPresent=2;
                            isActive=1;
                            etisLate.setVisibility(View.VISIBLE);
                        }else if(optionId==4){
                            isPresent=0;
                            isActive=1;
                            etisLate.setVisibility(View.GONE);
                            etisLate.setText("");
                        }
                        if(index<LocationsCls.questionAns.size()) {

                            LocationsCls.questionAns.get(index).setQuestionId(questionsbean.getId());
                            LocationsCls.questionAns.get(index).setOptionId(optionId);
                            LocationsCls.questionAns.get(index).setIsempCode(1);
                            LocationsCls.questionAns.get(index).setEmp_code(etempCode.getText()+"");
                            LocationsCls.questionAns.get(index).setLateDescriptin( etisLate.getText()+"");
                            LocationsCls.questionAns.get(index).setIs_active(isActive);
                            LocationsCls.questionAns.get(index).setIsPresent(isPresent);
                            //Toast.makeText(getActivity(),"question:"+questionId+",OptionId:"+optionId+",isHr:"+isHr,Toast.LENGTH_SHORT).show();

                        }else{
                            LocationsCls.questionAns.add(new Answers(questionId,optionId,etisLate.getText()+"",1,etempCode.getText()+"",isActive, isPresent));
                            //Toast.makeText(getActivity(),"Add question:"+questionId+",OptionId:"+optionId+",isHr"+isHr,Toast.LENGTH_SHORT).show();
                        }
                        if(isHr==2 && optionId==8){
                            for( ;index<questionsbeanArrayList.size();  index++){
                                questionsbean=questionsbeanArrayList.get(index);
                                if(index<LocationsCls.questionAns.size()) {

                                    LocationsCls.questionAns.get(index).setQuestionId(questionsbean.getId());
                                    LocationsCls.questionAns.get(index).setOptionId(8);
                                    LocationsCls.questionAns.get(index).setIsempCode(1);
                                    LocationsCls.questionAns.get(index).setEmp_code(etempCode.getText()+"");
                                    LocationsCls.questionAns.get(index).setLateDescriptin( etisLate.getText()+"");
                                    LocationsCls.questionAns.get(index).setIs_active(1);
                                    LocationsCls.questionAns.get(index).setIsPresent(0);

                                }else{
                                    LocationsCls.questionAns.add(new Answers(questionsbean.getId(),8,etisLate.getText()+"",1,etempCode.getText()+"",1, 0));
                                }

                            }
                            /*attendanceFragment fragment=new attendanceFragment();
                            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_main,fragment);
                            fragmentTransaction.commit();*/
                            //Toast.makeText(getActivity(),"question list:"+questionsbeanArrayList.size()+",QUESTION OPTION:"+LocationsCls.questionAns.size(),Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getActivity(),"Sonologist is Not available",Toast.LENGTH_SHORT).show();
                        }else{
                            index++;
                            answers=new Answers();
                            setOptions(index);
                        }
                        //tvMsg.setText("");
                    }
                    break;
                }
                case R.id.btnPrevious:{
                    int isPresent=1;
                    //int isActive=0;
                    if(optionId==7){
                        isPresent=2;
                        //isActive=1;
                        etisLate.setVisibility(View.VISIBLE);
                    }else if(optionId==5){
                        isPresent=2;
                        //isActive=1;
                        etisLate.setVisibility(View.VISIBLE);
                    }else if(optionId==4){
                        isPresent=0;
                        //isActive=1;
                        etisLate.setVisibility(View.GONE);
                        etisLate.setText("");
                    }
                    if(index<LocationsCls.questionAns.size()) {

                        LocationsCls.questionAns.get(index).setQuestionId(questionsbean.getId());
                        LocationsCls.questionAns.get(index).setOptionId(optionId);
                        LocationsCls.questionAns.get(index).setIsempCode(1);
                        LocationsCls.questionAns.get(index).setEmp_code(etempCode.getText()+"");
                        LocationsCls.questionAns.get(index).setLateDescriptin( etisLate.getText()+"");
                        LocationsCls.questionAns.get(index).setIs_active(1);
                        LocationsCls.questionAns.get(index).setIsPresent(isPresent);

                    }else{
                        //LocationsCls.questionAns.add(new Answers(questionId,optionId,etisLate.getText()+"",1,etempCode.getText()+"",1, isPresent));
                    }

                    index--;
                    answers=new Answers();
                    setOptions(index);

                    break;
                }case R.id.btnCancel:{
                    LocationsCls.questionAns=new ArrayList<>();
                        getActivity().finish();
                    break;
                }case R.id.btnSubmit:{
                    String msg="";
                    try {
                        //if(LocationsCls.latitude!=0.0 && LocationsCls.longitude!=0.0) {

                        /*imgName=""+MasterVisitCls.code+".jpg";
                        imgsource=imgsource+""+imgName + ".jpg";

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        imageFile = getImage();
                        imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imgName);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                        int count = 1;
                        startActivityForResult(intent, count);


                        final Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        MasterVisitCls.endTime=df.format(c.getTime());
                        String msg=controller.insertVisit(MasterVisitCls.code,MasterVisitCls.hfId,MasterVisitCls.phfId,MasterVisitCls.empId,
                                MasterVisitCls.startTime,MasterVisitCls.endTime,MasterVisitCls.isActive,LocationsCls.longitude+"",LocationsCls.latitude+"",imgsource,"mo_empcode","fmo_empcode",LocationsCls.questionAns);
                        tvMsg.setText(msg);
                        LocationsCls.chkList=0;

                        Intent i = new Intent(this.getActivity(), MainActivity.class);
                        startActivity(i);*/

                        int isPresent = 1;
                        int isActive = 0;
                        if (optionId == 7) {
                            isPresent = 2;
                            isActive = 1;
                            etisLate.setVisibility(View.VISIBLE);
                        } else if (optionId == 5) {
                            isPresent = 2;
                            isActive = 1;
                            etisLate.setVisibility(View.VISIBLE);
                        } else if (optionId == 4) {
                            isPresent = 0;
                            isActive = 1;
                            etisLate.setVisibility(View.GONE);
                            etisLate.setText("");
                        }
                        LocationsCls.questionAns.add(new Answers(questionId, optionId, etisLate.getText() + "", 1, etempCode.getText() + "", 1, isPresent));

                        msg = controller.updateEmployees(MasterVisitCls.mo_empCode,MasterVisitCls.code);

                        if (msg.equals("Updated")) {
                            final Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            MasterVisitCls.endTime = df.format(c.getTime());
                            msg = controller.insertVisit(MasterVisitCls.code, MasterVisitCls.hfId, MasterVisitCls.phfId, MasterVisitCls.empId,
                                    MasterVisitCls.startTime, MasterVisitCls.endTime, MasterVisitCls.isActive, LocationsCls.longitude + "", LocationsCls.latitude + "", imgsource, ""+MasterVisitCls.mo_empCode, "fmo_empcode", LocationsCls.questionAns, LocationsCls.attendanceBeanArrayList);
                            tvMsg.setText(msg);
                            LocationsCls.chkList = 0;
                            if (msg.equals("Inserted")) {
                                MasterVisitCls.code = null;
                                MasterVisitCls.hfId = 0;
                                MasterVisitCls.phfId = 0;
                                MasterVisitCls.empId = 0;
                                MasterVisitCls.startTime = null;
                                MasterVisitCls.endTime = null;
                                MasterVisitCls.isActive = 0;
                                MasterVisitCls.mo_empCode = "";
                                LocationsCls.longitude = 0.0;
                                LocationsCls.latitude = 0.0;

                                imgsource = null;
                                LocationsCls.questionAns = new ArrayList<>();
                                LocationsCls.attendanceBeanArrayList = new ArrayList<>();

                                MasterVisitCls.isSuccess = "Visit has been Submitted....";
                                Toast.makeText(getActivity(), "Visit has been " + msg, Toast.LENGTH_LONG).show();

                            /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            imageFile = getImage();
                            imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imgName);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                            int count = 1;
                            startActivityForResult(intent, count);*/

                                VisithfFragment fragment = new VisithfFragment();
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.content_main, fragment);
                                fragmentTransaction.commit();
                            } else {
                                tvMsg.setText("Error:" + msg);
                            }
                        } else {

                            tvMsg.setText("Error:" + msg);
                        }

                    /*attendanceFragment fragment=new attendanceFragment();
                    FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_main,fragment);
                    fragmentTransaction.commit();*/


                        //}else {
                        //   tvMsg.setText("Answers:" + LocationsCls.questionAns.size()+",Lat:"+LocationsCls.latitude+",Log:"+LocationsCls.longitude);
                        //}
                    }catch (Exception ex){
                        tvMsg.setText("Error:" + msg);
                    }
                    break;
                }
            }
        }catch (Exception ex){

        }



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if(requestCode==0){
                switch (resultCode){

                    case Activity.RESULT_OK:{
                        if(imageFile.exists()){
                            Toast.makeText(getActivity(),"Path:"+imageFile.getAbsolutePath(),Toast.LENGTH_LONG);
                        }
                        else{
                            Toast.makeText(getActivity(),"Error in path:",Toast.LENGTH_LONG);

                        }

                    }
                    case  Activity.RESULT_CANCELED:{


                    }

                }

            }

        }catch (Exception ex){
            Toast.makeText(getActivity(),"Error imgage Result:"+ex.getMessage(),Toast.LENGTH_LONG);
        }

    }

    public  void setOptions(int index){
        try{
            if(index==(questionsbeanArrayList.size()-1)){
                //btnNext.setEnabled(false);
                btnNext.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.VISIBLE);
            }else{
                //btnNext.setEnabled(true);
                btnNext.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.GONE);
            }
            if(index<=0){
                //btnPrevious.setEnabled(false);

                btnPrevious.setVisibility(View.INVISIBLE);
            }else{
                //btnPrevious.setEnabled(true);
                btnPrevious.setVisibility(View.VISIBLE);
            }
            questionsbean=questionsbeanArrayList.get(index);
            tvCat.setText("Category:"+questionsbean.getQues_cat());
            tvQuestion.setText("Q:"+(index+1)+") "+questionsbean.getQuestion());


            rbtn.clearCheck();
            rbtn=(RadioGroup)getActivity().findViewById(R.id.rbtn);
            rbtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    try {
                        int a = rbtn.indexOfChild(getActivity().findViewById(checkedId));
                        optionId=getActivity().findViewById(checkedId).getId();
                        if(optionId==7){
                            etisLate.setVisibility(View.VISIBLE);
                        }else{
                            etisLate.setVisibility(View.GONE);
                            etisLate.setText("");
                        }

                        ischecked=1;
                    }catch (Exception ex){
                        //Toast.makeText(getActivity(),"Error:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
            rbtn.removeAllViews();
            optionBeanArrayList=controller.getOptions(questionsbean.getId());
            questionId=questionsbean.getId();
            isHr=questionsbean.getIs_hr();
            //tvMsg.setText("is_hr:"+questionsbean.getIs_hr()+"\nMo_fmo_both:"+questionsbean.getMo_fmo_both());

            for (int i = 0; i < optionBeanArrayList.size(); i++) {
                optionBean=optionBeanArrayList.get(i);
                RadioButton rbn = new RadioButton(getActivity());
                rbn.setTextColor(Color.BLACK);
                etisLate.setVisibility(View.GONE);
                rbn.setId(optionBean.getOptionId());
                rbn.setText(""+optionBean.getOption());
                //etempCode.setText("");
                if(index<LocationsCls.questionAns.size()) {
                    for(Answers ans:LocationsCls.questionAns){
                        if(ans.getQuestionId()==questionsbean.getId() && optionBean.getOptionId()==ans.getOptionId()){
                            rbn.setChecked(true);

                            optionId = ans.getOptionId();
                            questionId=ans.questionId;
                            ischecked=1;
                            if(optionId==7){
                                etisLate.setVisibility(View.VISIBLE);
                                etempCode.setText(ans.getEmp_code());
                                etisLate.setText(ans.getLateDescriptin());
                            }else if(optionId==4){
                                etisLate.setVisibility(View.VISIBLE);
                                etempCode.setText(ans.getEmp_code());
                                etisLate.setText(ans.getLateDescriptin());
                            } else{
                                etisLate.setVisibility(View.GONE);
                                etempCode.setText(ans.getEmp_code());
                                etisLate.setText("");
                            }
                        }
                    }
                }else {
                    if (i == 0) {
                        ischecked=0;
                        if(questionsbean.getId()==5){
                            Employee employee=controller.getEmployee(MasterVisitCls.hfId,questionId);
                            if(employee!=null) {
                                tvQuestion.setText("Q:" + (index + 1) + ") " + questionsbean.getQuestion() + "\n " + employee.getEMP_NAME());
                                etempCode.setText("" + employee.getEMP_CODE());
                            }else{
                                etempCode.setText("");
                            }

                        }else if(questionsbean.getId()==6){
                            Employee employee=controller.getEmployee(MasterVisitCls.hfId,questionId);
                            if(employee!=null) {
                                tvQuestion.setText("Q:" + (index + 1) + ") " + questionsbean.getQuestion() + "\n " + employee.getEMP_NAME());
                                etempCode.setText("" + employee.getEMP_CODE());
                            }else{
                                etempCode.setText("");
                            }

                        }


                    }
                }
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(15, 15, 15, 15);

                rbn.setLayoutParams(params);
                rbtn.addView(rbn);

                //radioGroup.addView(radioButtonView);
            }

            if(questionsbean.getIs_hr()==1 && questionsbean.getMo_fmo_both()==1){
                //tvMsg.setText("MO");
                etempCode.setVisibility(View.VISIBLE);
                chkhasempCode.setVisibility(View.VISIBLE);
                etempCode.setEnabled(false);
                chkhasempCode.setChecked(false);



            }else if(questionsbean.getIs_hr()==1 && questionsbean.getMo_fmo_both()==2){
                //tvMsg.setText("FMO");
                etempCode.setEnabled(false);
                etempCode.setVisibility(View.VISIBLE);
                chkhasempCode.setVisibility(View.VISIBLE);
                chkhasempCode.setChecked(false);


            }else{
                etempCode.setVisibility(View.GONE);
                chkhasempCode.setVisibility(View.GONE);
                etempCode.setText("");
                chkhasempCode.setChecked(false);
            }


        }catch (Exception ex){
            Toast.makeText(getActivity(),"Error to set Options:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private  File getImage(){

        File folder=new File("sdcard/camera");
        if(!folder.exists()){
            folder.mkdir();
        }
        File img_file=new File(folder,"cam_image");
        return img_file;

    }
}
