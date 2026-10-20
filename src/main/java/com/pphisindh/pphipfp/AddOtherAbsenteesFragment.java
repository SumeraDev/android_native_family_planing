package com.pphisindh.pphipfp;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pphisindh.pphipfp.Beans.AttendanceBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOtherAbsenteesFragment extends Fragment implements View.OnClickListener{


    Button btnAdd;
    Button btnSubmit;
    EditText etempCode;
    TextView tvMsg;


    String imgName="";
    String imgsource="http://jobs.pphisindh.org/PPHIAndroidTest/epicardpics/";
    File imageFile;

    SQLiteController controller;


    public AddOtherAbsenteesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_other_absentees, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{

            controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
            btnAdd=(Button)getActivity().findViewById(R.id.btnAdd);
            btnSubmit=(Button)getActivity().findViewById(R.id.btnSubmit);
            etempCode=(EditText)getActivity().findViewById(R.id.etempCode);
            tvMsg=(TextView)getActivity().findViewById(R.id.tvMsg);

            btnAdd.setOnClickListener(this);
            btnSubmit.setOnClickListener(this);



        }catch (Exception ex){

        }
    }



    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnAdd: {
                    if(etempCode.getText()!=null && etempCode.getText()+""!="") {
                        LocationsCls.attendanceBeanArrayList.add(new AttendanceBean(MasterVisitCls.code, 7, 4, "None", 1, etempCode.getText() + "", 1, 0));
                        tvMsg.setText("Empcode:"+etempCode.getText()+" is added...!");
                        etempCode.setText("");
                    }else {
                        Toast.makeText(getActivity(), "Please fill field....!",Toast.LENGTH_LONG).show();
                    }
                    break;
                }case R.id.btnSubmit: {
                    try {
                        //if(LocationsCls.latitude!=0.0 && LocationsCls.longitude!=0.0) {

                            imgName = "" + MasterVisitCls.code + ".jpg";
                            imgsource = imgsource + "" + imgName + ".jpg";




                            final Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            MasterVisitCls.endTime = df.format(c.getTime());
                            String msg = controller.insertVisit(MasterVisitCls.code, MasterVisitCls.hfId, MasterVisitCls.phfId, MasterVisitCls.empId,
                                    MasterVisitCls.startTime, MasterVisitCls.endTime, MasterVisitCls.isActive, LocationsCls.longitude + "", LocationsCls.latitude + "", imgsource, "mo_empcode", "fmo_empcode", LocationsCls.questionAns,LocationsCls.attendanceBeanArrayList);
                            tvMsg.setText(msg);
                            LocationsCls.chkList = 0;
                            if(msg.equals("Inserted")) {
                                MasterVisitCls.code = null;
                                MasterVisitCls.hfId = 0;
                                MasterVisitCls.phfId = 0;
                                MasterVisitCls.empId = 0;
                                MasterVisitCls.startTime = null;
                                MasterVisitCls.endTime = null;
                                MasterVisitCls.isActive = 0;
                                LocationsCls.longitude = 0.0;
                                LocationsCls.latitude = 0.0;
                                imgsource = null;
                                LocationsCls.questionAns = new ArrayList<>();
                                LocationsCls.attendanceBeanArrayList = new ArrayList<>();

                                MasterVisitCls.isSuccess="Visit has been Submitted....";

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                imageFile = getImage();
                                imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imgName);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                                int count = 1;
                                startActivityForResult(intent, count);

                                RegisterVisitFragment fragment = new RegisterVisitFragment();
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.content_main, fragment);
                                fragmentTransaction.commit();
                            }else {
                                tvMsg.setText("Error:"+msg);
                            }

                            /*Intent i = new Intent(this.getActivity(), MainActivity.class);
                            startActivity(i);
                        }else {
                            tvMsg.setText("Location not Found....!");
                        }*/


                    }catch (Exception ex){
                        tvMsg.setText("Error:"+ex.getMessage());
                        RegisterVisitFragment fragment = new RegisterVisitFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_main, fragment);
                        fragmentTransaction.commit();
                    }

                    break;
                }
            }
        }catch (Exception ex){
            Toast.makeText(getActivity(), "Error:" + ex.getMessage(),Toast.LENGTH_LONG).show();
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
    private  File getImage(){

        File folder=new File("sdcard/camera");
        if(!folder.exists()){
            folder.mkdir();
        }
        File img_file=new File(folder,"cam_image");
        return img_file;

    }

}
