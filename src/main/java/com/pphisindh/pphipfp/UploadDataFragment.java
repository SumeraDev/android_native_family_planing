package com.pphisindh.pphipfp;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pphisindh.pphipfp.Beans.AttendanceBean;
import com.pphisindh.pphipfp.Beans.Tbl_emp_serving;
import com.pphisindh.pphipfp.Beans.Tbl_hf_services_avail;
import com.pphisindh.pphipfp.Beans.VisitDetailBean;
import com.pphisindh.pphipfp.Beans.VisitMasterBean;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadDataFragment extends Fragment implements View.OnClickListener{

    TextView tvMsg;

    Button btnUploadVisits;
    Button btnVisitDet;
    Button btnsetup;
    Button btnAttendance;
    Button btnhfservices;


    ProgressDialog loading;
    int empId;
    String msg;//203.170.79.130
    String pathName= ApplicationVariables.urlPublic;
    //String pathName="https://tms.pphisindh.org/mom/";
    //"http://jobs.pphisindh.org/mom/";
    SQLiteController controller;
    ArrayList<VisitMasterBean> arrayList;
    int index=0;

    String mo_empcode;
    String fmo_empcode;

    ArrayList<VisitDetailBean> visitDetailBeanArrayList;
    ArrayList<Tbl_emp_serving> emp_servings;
    ArrayList<Tbl_hf_services_avail> hf_services_avails;
    ArrayList<AttendanceBean> attendanceBeanArrayList;
    public UploadDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_data, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{

            tvMsg=(TextView)getActivity().findViewById(R.id.tvMsg);

            btnUploadVisits=(Button)getActivity().findViewById(R.id.btnUploadVisits);
          //  btnVisitDet=(Button)getActivity().findViewById(R.id.btnVisitDet);
           // btnsetup=(Button)getActivity().findViewById(R.id.btnsetup);
         //   btnAttendance=(Button)getActivity().findViewById(R.id.btnAttendance);
      //      btnhfservices=(Button)getActivity().findViewById(R.id.btnhfservices);


            btnUploadVisits.setOnClickListener(this);
//            btnVisitDet.setOnClickListener(this);
//            btnAttendance.setOnClickListener(this);
//            btnsetup.setOnClickListener(this);
//            btnhfservices.setOnClickListener(this);

            controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
            msg="";

        }catch (Exception ex){
            tvMsg.setText("Error:"+ex.getMessage());
        }
    }
    @Override
    public void onClick(View v) {
     //   try{
//            switch (v.getId()){
//                case R.id.btnUploadVisits:{

                    try {
                        arrayList = controller.uploadVisits();
                        if (arrayList.size() > 0) {
                            new UploadVisits().execute();
                            //setLoader("Uploading Visits");
                        } else {
                            tvMsg.setText("You don't have enough Visits to Upload.....!");
                        }
                    }catch (Exception ex){
                        tvMsg.setText("Error in Upload Visit:"+ex.getMessage());
                    }
//                    break;
//                }case R.id.btnVisitDet:{
                    try {

                        visitDetailBeanArrayList = controller.uploadVisitsDetail();
                        //String count = ""+controller.uploadVisitsDetail().size();
                        tvMsg.setText("Please Wait for Uploading:");

                        if (visitDetailBeanArrayList.size() > 0) {

                            new UploadVisitDetail().execute();

                        } else {
                            tvMsg.setText("You don't have enough Visit Details to Upload.....!");
                        }
                    }catch (Exception ex){
                        tvMsg.setText("Error in Upload Visit Detail:"+ex.getMessage());
                    }
//                    break;
//                }case R.id.btnsetup:{
                    try{
                        emp_servings=new ArrayList<>();

                        emp_servings = controller.uploadEmpservings();
                        tvMsg.setText("Please Wait for Uploading:");

                        if (emp_servings.size() > 0) {

                            new UploadEmployeeservings().execute();

                        } else {
                            tvMsg.setText("You don't have enough Setup to Upload.....!");
                        }

                    }catch (Exception ex){
                        tvMsg.setText("Error to Upload.....!");
                    }
//                    break;
//                }case R.id.btnhfservices:{
                    try{
                        hf_services_avails=new ArrayList<>();

                        hf_services_avails = controller.uploadhfservices(getActivity());
                        tvMsg.setText("Please Wait for Uploading:");

                        if (hf_services_avails.size() > 0) {

                            new UploadHFServices().execute();

                        } else {
                            tvMsg.setText("You don't have enough hf_services_avails to Upload.....!");
                        }

                    }catch (Exception ex){
                        tvMsg.append("Error to Upload :"+ex.getMessage()+","+hf_services_avails.size());
                    }
//                    break;
//                }

//                case R.id.btnAttendance:{
//                    try {
//
//                        attendanceBeanArrayList = controller.uploadAttendance();
//
//                        tvMsg.setText("Please Wait for Uploading:");
//                        if (attendanceBeanArrayList.size() > 0) {
//
//                            new UploadAttendance().execute();
//
//                        } else {
//                            tvMsg.setText("You don't have enough Attendance to Upload.....!");
//                        }
//                    }catch (Exception ex){
//                        tvMsg.setText("Error in Upload Attendance:"+ex.getMessage());
//                    }
//                    break;
//                } -->
//            }
//        }catch (Exception ex){
//
//            tvMsg.setText("Error:"+ex.getMessage());
//
//        }
    }
    public void setLoader(String msgType){
        loading = new ProgressDialog(getActivity());
        loading.setMessage("Uploading Visits..."); // Setting Message
        loading.setTitle(msgType); // Setting Title
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        loading.show(); // Display Progress Dialog
        loading.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                while(true){
                    if(msg.length()>1){
                        loading.dismiss();
                        //msg="";
                        break;
                    }else{

                    }
                }

            }
        }).start();
    }

    class UploadVisits extends AsyncTask<String, String, String> {

        JSONObject jsonObject =null;
        String response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("Finished:"+arrayList.size()+"/"+index);

            } catch (Exception ex) {

            }
        }
        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                String path = pathName+"UploadVisits.php";

                for( index=0;    index<arrayList.size() ; ){
                    try {

                        VisitMasterBean visitMasterBean = arrayList.get(index);
                        URL url = new URL(path);
                        JSONObject postDataParams = new JSONObject();

                        postDataParams.put("code", visitMasterBean.getCode() + "");
                        postDataParams.put("hfid", visitMasterBean.getHfid() + "");
                        postDataParams.put("phfid", visitMasterBean.getPhfid() + "");
                        postDataParams.put("emp_id", visitMasterBean.getEmp_id() + "");
                        postDataParams.put("start_time", visitMasterBean.getStart_time() + "");
                        postDataParams.put("end_time", visitMasterBean.getEnd_time() + "");
                        postDataParams.put("is_active", visitMasterBean.getIs_active() + "");
                        postDataParams.put("longitude", visitMasterBean.getLongitude() + "");
                        postDataParams.put("latitude", visitMasterBean.getLatitude() + "");
                        postDataParams.put("imgsource", visitMasterBean.getImgsource() + "");

                        postDataParams.put("mo_empcode", visitMasterBean.getMo_empcode() + "");
                        postDataParams.put("fmo_empcode", visitMasterBean.getFmo_empcode() + "");
                        postDataParams.put("qtr_id", visitMasterBean.getQtr_id() + "");
                         mo_empcode=visitMasterBean.getMo_empcode();
                         fmo_empcode=visitMasterBean.getFmo_empcode();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMsg.setText("Visits:" + arrayList.size() + "/" + index+",Mo:"+mo_empcode+",Fmo:"+fmo_empcode);
                            }
                        });

                        Log.e("params", postDataParams.toString());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(25000 /* milliseconds */);
                        //conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(getPostDataString(postDataParams));

                        writer.flush();
                        writer.close();
                        os.close();

                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpsURLConnection.HTTP_OK) {

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            response = in.readLine();
                            if (response != null && response != "[]") {
                                jsonObject= new JSONObject(response);
                                if (jsonObject != null) {
                                    msg = controller.updateVisitMaster(visitMasterBean.getCode());
                                    if (jsonObject.getString("msg").equals("Inserted")) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tvMsg.setText("Visits:" + arrayList.size() + "/" + index+",Mo:"+mo_empcode+",Fmo:"+fmo_empcode);
                                            }
                                        });
                                    }
                                }
                            } else {
                                msg = "Response not found" ;
                            }

                            in.close();

                        } else {
                            msg = "Response not found";

                        }
                    }catch (Exception ex){
                        final String msg="Error uploading:"+ex.getMessage();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMsg.setText("Visits:" + arrayList.size() + "/" + index+","+msg);
                            }
                        });
                    }
                    index++;

                }

            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMsg.setText(msg);
                    }
                });
            }

            return  null;
        }

    }
    class UploadVisitDetail extends AsyncTask<String, String, String> {

        JSONObject jsonObject =null;
        String response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("Finished:"+visitDetailBeanArrayList.size()+"/"+index);

            } catch (Exception ex) {

            }
        }
        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                String path = pathName+"UploadVisitDetails.php";

                for( index=0;    index<visitDetailBeanArrayList.size() ; ){
                    try {

                        VisitDetailBean visitDetailBean = visitDetailBeanArrayList.get(index);
                        URL url = new URL(path);
                        JSONObject postDataParams = new JSONObject();


                        postDataParams.put("vm_code", visitDetailBean.getVm_code() + "");
                        postDataParams.put("vm_id", visitDetailBean.getVm_id() + "");
                        postDataParams.put("qid", visitDetailBean.getQid() + "");
                        postDataParams.put("oid", visitDetailBean.getOid() + "");
                        postDataParams.put("is_active", visitDetailBean.getIs_active() + "");
                        postDataParams.put("qtr_id", controller.getQtr(visitDetailBean.getVm_code() + "") + "");




                        Log.e("params", postDataParams.toString());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(25000 /* milliseconds */);
                        //conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(getPostDataString(postDataParams));

                        writer.flush();
                        writer.close();
                        os.close();

                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpsURLConnection.HTTP_OK) {

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            response = in.readLine();
                            if (response != null && response != "[]") {
                                jsonObject= new JSONObject(response);
                                if (jsonObject != null) {

                                    if (jsonObject.getString("msg").equals("Inserted")) {
                                        msg = controller.updateVisitMaster(visitDetailBean.getVm_code(),visitDetailBean.getQid());
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    tvMsg.setText("Visit Detail:" + visitDetailBeanArrayList.size() + "/" + index + "\n" +msg);
                                                }catch (Exception ex){}
                                            }
                                        });

                                    }

                                }
                            } else {
                                msg = "Response not found" ;
                            }
                            index++;
                            in.close();

                        } else {
                            msg = "Response not found";

                        }
                    }catch (Exception ex){
                        final String msg="Error uploading:"+ex.getMessage();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMsg.setText("Visit Detail:" + visitDetailBeanArrayList.size() + "/" + index+","+msg);
                            }
                        });
                    }

                }

            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMsg.setText(msg);
                    }
                });
            }

            return  null;
        }

    }
    class UploadEmployeeservings extends AsyncTask<String, String, String> {

        JSONObject jsonObject =null;
        String response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\nFinished:"+emp_servings.size()+"/"+index+","+msg);

            } catch (Exception ex) {

            }
        }
        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                String path = pathName+"Uploadempservings.php";
                for( index=0;    index<emp_servings.size() ; ){
                    try {

                        Tbl_emp_serving emp_serving = emp_servings.get(index);
                        URL url = new URL(path);
                        JSONObject postDataParams = new JSONObject();


                        //id,ques_catId ,fk_hfid ,hfid ,qtr_id ,visit_masterId ,emp_code ,empname ,is_active
                        postDataParams.put("id", emp_serving.getId() + "");
                        postDataParams.put("ques_catId", emp_serving.getQues_catId() + "");
                        postDataParams.put("fk_hfid", emp_serving.getFk_hfid() + "");
                        postDataParams.put("hfid", emp_serving.getHfid() + "");
                        postDataParams.put("qtr_id", emp_serving.getQtr_id() + "");

                        postDataParams.put("visit_masterId", emp_serving.getVisit_masterId() + "");
                        postDataParams.put("emp_code", emp_serving.getEmp_code() + "");
                        postDataParams.put("empname", emp_serving.getEmpname() + "");
                        postDataParams.put("is_active", emp_serving.getIs_active() + "");

                        Log.e("params", postDataParams.toString());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(25000 /* milliseconds */);
                        //conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(getPostDataString(postDataParams));

                        writer.flush();
                        writer.close();
                        os.close();

                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpsURLConnection.HTTP_OK) {

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            response = in.readLine();
                            if (response != null && response != "[]") {
                                jsonObject= new JSONObject(response);
                                if (jsonObject != null) {

                                    if (jsonObject.getString("msg").equals("Inserted")) {
                                        msg=controller.updateEmpservrings(emp_serving.getId());
                                        //msg = controller.updateAttendance(attendanceBean.getVm_code(),attendanceBean.getEmp_code());
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    tvMsg.setText("Emp_servings:" + emp_servings.size() + "/" + index + "\n" +msg);
                                                }catch (Exception ex){}
                                            }
                                        });

                                    }

                                }
                            } else {
                                msg = "Response not found" ;
                            }
                            index++;
                            in.close();

                        } else {
                            msg = "Response not found";

                        }
                    }catch (Exception ex){
                        final String msg="Error uploading:"+ex.getMessage();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMsg.append("emp_servings:" + emp_servings.size() + "/" + index+","+msg);
                            }
                        });
                    }

                }

            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMsg.setText(msg);
                    }
                });
            }

            return  null;
        }

    }
    class UploadHFServices extends AsyncTask<String, String, String> {

        JSONObject jsonObject =null;
        String response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\nFinished:"+hf_services_avails.size()+"/"+index+","+msg);

            } catch (Exception ex) {

            }
        }
        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                String path = pathName+"Uploadhfservices.php";
              //  Log.i("testing","testing"+hf_services_avails.size());
                for( index=0;    index<hf_services_avails.size() ; ){
                    try {

                        Tbl_hf_services_avail hfservices = hf_services_avails.get(index);
                        URL url = new URL(path);
                        JSONObject postDataParams = new JSONObject();


                        //id ,serviceType_id ,fk_hfid ,hfid ,qtr_id ,is_active
                        postDataParams.put("id", hfservices.getId() + "");
                        postDataParams.put("serviceType_id", hfservices.getServiceType_id() + "");
                        postDataParams.put("fk_hfid", hfservices.getFk_hfid() + "");
                        postDataParams.put("hfid", hfservices.getHfid() + "");
                        postDataParams.put("qtr_id", hfservices.getQtr_id() + "");
                        postDataParams.put("is_active", hfservices.getIs_active() + "");


                        Log.e("params", postDataParams.toString());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(25000 /* milliseconds */);
                        //conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(getPostDataString(postDataParams));

                        writer.flush();
                        writer.close();
                        os.close();

                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpsURLConnection.HTTP_OK) {

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            response = in.readLine();
                            Log.e("pajamas", response.toString());

                            if (response != null && response != "[]") {
                                jsonObject= new JSONObject(response);
                               // Log.e("pajamasr", response.toString());
                              //  Log.e("pajamas", jsonObject.toString());

                                if (jsonObject != null) {

                                    if (jsonObject.getString("msg").equals("Inserted")) {
                                        msg=controller.updatehfservices(hfservices.getId());
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    tvMsg.setText("HF Servies:" + hf_services_avails.size() + "/" + index + "\n" +msg);
                                                }catch (Exception ex){}
                                            }
                                        });

                                    }

                                }
                            } else {
                                msg = "Response not found" ;
                            }
                            index++;
                            in.close();

                        } else {
                            msg = "Response not found";

                        }
                    }catch (Exception ex){
                        final String msg="Error uploading:"+ex.getMessage();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMsg.append("HF Services:" + hf_services_avails.size() + "/" + index+","+msg);
                            }
                        });
                    }

                }

            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMsg.append(msg);
                    }
                });
            }

            return  null;
        }

    }


    class UploadAttendance extends AsyncTask<String, String, String> {

        JSONObject jsonObject =null;
        String response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("Finished:"+attendanceBeanArrayList.size()+"/"+index);

            } catch (Exception ex) {

            }
        }
        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }

        @Override
        protected String doInBackground(String... params) {

            try {


                String path = pathName+"UploadAttendance.php";

                for( index=0;    index<attendanceBeanArrayList.size() ; ){
                    try {

                        AttendanceBean attendanceBean = attendanceBeanArrayList.get(index);
                        URL url = new URL(path);
                        JSONObject postDataParams = new JSONObject();


                        //vm_code,qId,oId,lateDescriptin,isempCode,emp_code,is_active,isPresent
                        postDataParams.put("vm_code", attendanceBean.getVm_code() + "");
                        postDataParams.put("qId", attendanceBean.getqId() + "");
                        postDataParams.put("oId", attendanceBean.getoId() + "");
                        postDataParams.put("lateDescriptin", attendanceBean.getLateDescriptin() + "");
                        postDataParams.put("isempCode", attendanceBean.getIsempCode() + "");

                        postDataParams.put("emp_code", attendanceBean.getEmp_code() + "");
                        postDataParams.put("is_active", attendanceBean.getIs_active() + "");
                        postDataParams.put("isPresent", attendanceBean.getIsPresent() + "");

                        Log.e("params", postDataParams.toString());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(25000 /* milliseconds */);
                        //conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(getPostDataString(postDataParams));

                        writer.flush();
                        writer.close();
                        os.close();

                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpsURLConnection.HTTP_OK) {

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            response = in.readLine();
                            if (response != null && response != "[]") {
                                jsonObject= new JSONObject(response);
                                if (jsonObject != null) {

                                    if (jsonObject.getString("msg").equals("Inserted")) {
                                        //msg = controller.updateAttendance(attendanceBean.getVm_code(),attendanceBean.getEmp_code());
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    tvMsg.setText("Attendance:" + attendanceBeanArrayList.size() + "/" + index + "\n" +msg);
                                                }catch (Exception ex){}
                                            }
                                        });

                                    }

                                }
                            } else {
                                msg = "Response not found" ;
                            }
                            index++;
                            in.close();

                        } else {
                            msg = "Response not found";

                        }
                    }catch (Exception ex){
                        final String msg="Error uploading:"+ex.getMessage();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMsg.setText("Attendance:" + attendanceBeanArrayList.size() + "/" + index+","+msg);
                            }
                        });
                    }

                }

            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMsg.setText(msg);
                    }
                });
            }

            return  null;
        }

    }


}
