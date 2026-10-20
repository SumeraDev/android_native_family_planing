package com.pphisindh.pphipfp;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pphisindh.pphipfp.Beans.VisitDetailBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
public class DownloadDataFragment extends Fragment implements View.OnClickListener{

    TextView tvMsg;
    Button btnUploadVisits;
    Button btnDownloadConfig;
    Button btnGeneratevisitfile;
    ProgressDialog loading;
    int empId,quarterId;
    String msg;//203.170.79.130
    //
    String pathName=ApplicationVariables.urlPublic;
    //"http://jobs.pphisindh.org/mom/";
    //String pathName="http://203.170.79.130/momClose10th/";
    SQLiteController controller;

    public DownloadDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_download_data, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{

             tvMsg=(TextView)getActivity().findViewById(R.id.tvMsg);
             tvMsg.setText("Click button");
             btnDownloadConfig=(Button)getActivity().findViewById(R.id.btnDownloadConfig);
             btnGeneratevisitfile=(Button)getActivity().findViewById(R.id.btnGeneratevisitfile);
             btnDownloadConfig.setOnClickListener(this);
             btnGeneratevisitfile.setOnClickListener(this);
             controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
             msg="";

        }catch (Exception ex){
            Toast.makeText(getActivity(),"Error in OnStart:"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()) {
                case R.id.btnDownloadConfig: {
                    msg = "";
                    tvMsg.setText("Please wait......\n");
                    new DownloadQTR().execute();
                    //setLoader("Downloading Config Data");
                    break;
                }case R.id.btnGeneratevisitfile: {
                    try {

                        tvMsg.setText("Please wait......\n");
                        ArrayList<VisitDetailBean> visitDetailBeanArrayList;
                        visitDetailBeanArrayList = controller.uploadVisitsDetail();
                        tvMsg.setText("");
                        for (int index = 0; index < visitDetailBeanArrayList.size(); index++) {
                            VisitDetailBean visitDetailBean = visitDetailBeanArrayList.get(index);
                            JSONObject postDataParams = new JSONObject();

                            postDataParams.put("vm_code", visitDetailBean.getVm_code() + "");
                            postDataParams.put("vm_id", visitDetailBean.getVm_id() + "");
                            postDataParams.put("qid", visitDetailBean.getQid() + "");
                            postDataParams.put("oid", visitDetailBean.getOid() + "");
                            postDataParams.put("is_active", visitDetailBean.getIs_active() + "");
                            tvMsg.append("" + postDataParams.toString());


                        }
                        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        String visitdetail = "visitdetail.txt";
                        File gpxfile = new File(root, visitdetail);
                        FileWriter writer = new FileWriter(gpxfile);
                        writer.append(tvMsg.getText());
                        writer.flush();
                        writer.close();
                        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(getActivity(), "Exception:"+ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }catch (Exception ex){
            //loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
            tvMsg.setText("Error:"+ex.getMessage());
            //loading.dismiss();
        }
    }



    public void setLoader(String msgType){
        loading = new ProgressDialog(getActivity());
        loading.setMessage("Loading..."); // Setting Message
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


    class DownloadQTR extends AsyncTask<String, String, String> {

        JSONArray jsonArray =null;

        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                //tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\n"+msg);
                msg="";
                new DownloadHealthFacilities().execute();
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
                empId=controller.getEmpId();
                String path = pathName+"insertQuarter.php";
                URL url = new URL(path);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("empId", empId + "");
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

                        jsonArray = new JSONArray(response);
                        if (jsonArray != null) {
                            msg = controller.downloadQTR(jsonArray);
                            if(msg.equals("Inserted")){
                                msg="Quarter:"+jsonArray.length();

                            }else{
                                msg="Quarter not Inserted:";
                            }

                        } else {
                            msg="Quarter not Downloaded:";
                        }
                    } else {
                        msg="Response not found"+jsonArray.length();
                    }
                    //return sb.toString();
                    in.close();

                } else {
                    msg="Response not found"+jsonArray.length();
                    //return new String("false : " + responseCode);
                }
            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
            }

            return  null;
        }

    }
    class DownloadHealthFacilities extends AsyncTask<String, String, String> {

        JSONArray jsonArray =null;
        //String pathName="http://jobs.pphisindh.org/mom/";
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                //tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\n"+msg);
                msg="";
                new DownloadEmployees().execute();
                //setLoader("Download Config Data");

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
                empId=controller.getEmpId();
                String path = pathName+"DownloadHealthFacilities.php";
                URL url = new URL(path);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("empId", empId + "");
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

                        jsonArray = new JSONArray(response);
                        if (jsonArray != null) {
                            msg = controller.downloadHealthFacility(jsonArray);
                            if(msg.equals("Inserted")){
                                msg="Health Facilities:"+jsonArray.length();
                            }else{
                                msg=msg+"Not Inserted"+empId+",";
                            }

                        } else {

                        }
                    } else {
                        msg="Response not found"+jsonArray.length();
                    }
                    //return sb.toString();
                    in.close();

                } else {
                    msg="Response not found"+jsonArray.length();
                    //return new String("false : " + responseCode);
                }
            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
            }

            return  null;
        }

    }
    class DownloadEmployees extends AsyncTask<String, String, String> {

        JSONArray jsonArray =null;
        //String pathName="http://jobs.pphisindh.org/mom/";
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                //tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\n"+msg);
                msg="";
                new DownloadQuestions().execute();
                //setLoader("Download Config Data :");
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
                empId=controller.getEmpId();
                quarterId=controller.getQuarteridActive();

                String path = pathName+"DownloadEmployees.php";
                URL url = new URL(path);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("empId", empId + "");
                postDataParams.put("qtr_id", quarterId + "");
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

                        jsonArray = new JSONArray(response);
                        if (jsonArray != null) {
                            msg = controller.downloadEmployees(jsonArray);
                            if(msg.equals("Inserted")){
                                msg="Employees:"+jsonArray.length();
                            }else{
                                msg="Employees Not Inserted"+empId;
                            }

                        } else {

                        }
                    } else {
                        msg="Response not found"+jsonArray.length();
                    }
                    //return sb.toString();
                    in.close();

                } else {
                    msg="Response not found"+jsonArray.length();
                    //return new String("false : " + responseCode);
                }
            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
            }

            return  null;
        }

    }

    class DownloadQuestions extends AsyncTask<String, String, String> {

        JSONArray jsonArray =null;
        //String pathName="http://jobs.pphisindh.org/mom/";
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                //tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\n"+msg+","+jsonArray.length());
                msg="";
                new DownloadOptions().execute();
                //setLoader("Downloading Config Data:");
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
                empId=controller.getEmpId();
                String path = pathName+"insertQuestions.php";
                URL url = new URL(path);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("empId", empId + "");
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

                        jsonArray = new JSONArray(response);
                        if (jsonArray != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    msg = controller.downloadquestions(jsonArray,getActivity());

                                }
                            });

                            if(msg.equals("Inserted")){
                                msg="Questions:"+jsonArray.length();
                            }else {
                                msg="Questions Not Inserted";
                            }

                        } else {
                            msg="Questions Not Downloaded";
                        }
                    } else {
                        msg="Response not found"+jsonArray.length();
                    }
                    //return sb.toString();
                    in.close();

                } else {
                    msg="Response not found"+jsonArray.length();
                    //return new String("false : " + responseCode);
                }
            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
            }

            return  null;
        }

    }
    class DownloadOptions extends AsyncTask<String, String, String> {

        JSONArray jsonArray =null;

        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                //tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\n"+msg);
                msg="";
                new DownloadQuestionOptions().execute();
                //setLoader("Downloading config data:");
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
                empId=controller.getEmpId();
                String path = pathName+"insertOptions.php";
                URL url = new URL(path);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("empId", empId + "");
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

                        jsonArray = new JSONArray(response);

                        Log.d("atifbhaigusseywala","atifbhaigusseywala"+jsonArray);

                        if (jsonArray != null) {
                            msg = controller.downloadOptions(jsonArray);
                            if(msg.equals("Inserted")){
                                msg="Options:"+jsonArray.length();
                            }else{
                                msg="Options not Inserted:";
                            }

                        } else {
                            msg="Options not Donwloaded:";
                        }
                    } else {
                        msg="Response not found"+jsonArray.length();
                    }
                    //return sb.toString();
                    in.close();

                } else {
                    msg="Response not found"+jsonArray.length();
                    //return new String("false : " + responseCode);
                }
            } catch (Exception ex) {
                msg = "EException" + ex.getStackTrace();
            }

            return  null;
        }

    }

    class DownloadQuestionOptions extends AsyncTask<String, String, String> {

        JSONArray jsonArray =null;

        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                //tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\n"+msg);
                msg="";
                new DownloadQuestioncateory().execute();
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
                empId=controller.getEmpId();
                String path = pathName+"insertQuesOptions.php";
                URL url = new URL(path);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("empId", empId + "");
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

                        jsonArray = new JSONArray(response);
                        Log.d("atifbhaigusseywalaa","atifbhaigusseywalaa"+jsonArray);
                        if (jsonArray != null) {
                            msg = controller.downloadQuestionsOptions(jsonArray);
                            if(msg.equals("Inserted")){
                                msg="Question Options:"+jsonArray.length();

                            }else{
                                msg="Question Options not Inserted:";
                            }

                        } else {
                            msg="Question Options not Downloaded:";
                        }
                    } else {
                        msg="Response not found"+jsonArray.length();
                    }
                    //return sb.toString();
                    in.close();

                } else {
                    msg="Response not found"+jsonArray.length();
                    //return new String("false : " + responseCode);
                }
            } catch (Exception ex) {
                msg = "FException" + ex.getStackTrace();
            }

            return  null;
        }

    }
    class DownloadQuestioncateory extends AsyncTask<String, String, String> {

        JSONArray jsonArray =null;

        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                //tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\n"+msg);
                msg="";
                new DownloadHFServices().execute();
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
                empId=controller.getEmpId();
                String path = pathName+"insertQuestionCategory.php";
                URL url = new URL(path);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("empId", empId + "");
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

                        jsonArray = new JSONArray(response);
                        if (jsonArray != null) {
                            msg = controller.downloadQuestionCat(jsonArray);
                            if(msg.equals("Inserted")){
                                msg="Question Category:"+jsonArray.length();

                            }else{
                                msg="Question Category not Inserted:";
                            }

                        } else {
                            msg="Question Category not Downloaded:";
                        }
                    } else {
                        msg="Response not found"+jsonArray.length();
                    }
                    //return sb.toString();
                    in.close();

                } else {
                    msg="Response not found"+jsonArray.length();
                    //return new String("false : " + responseCode);
                }
            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
            }

            return  null;
        }

    }
    class DownloadHFServices extends AsyncTask<String, String, String> {

        JSONArray jsonArray =null;

        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                //tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\n"+msg);
                msg="";
                new DownloadMasterVisits().execute();
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
                empId=controller.getEmpId();
                String path = pathName+"insertHFServices.php";
                URL url = new URL(path);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("empId", empId + "");
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

                        jsonArray = new JSONArray(response);
                        if (jsonArray != null) {
                            msg = controller.downloadhfservices(jsonArray);
                            if(msg.equals("Inserted")){
                                msg="HF Services:"+jsonArray.length();

                            }else{
                                msg="HF Services not Inserted:";
                            }

                        } else {
                            msg="HF Services not Downloaded:";
                        }
                    } else {
                        msg="Response not found"+jsonArray.length();
                    }
                    //return sb.toString();
                    in.close();

                } else {
                    msg="Response not found"+jsonArray.length();
                    //return new String("false : " + responseCode);
                }
            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
            }

            return  null;
        }

    }

    class DownloadMasterVisits extends AsyncTask<String, String, String> {

        JSONArray jsonArray =null;

        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                //tvMsg.setText("Please wait....");
                msg="";
            }catch (Exception ex){

            }
        }

        @Override
        protected void onPostExecute(String str) {
            try {
                tvMsg.append("\n"+msg);
                msg="";
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
                empId=controller.getEmpId();
                String path = pathName+"DownloadMastervisit.php";
                URL url = new URL(path);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("empid", empId + "");
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

                        jsonArray = new JSONArray(response);
                        if (jsonArray != null) {
                            msg = controller.downloadMasterVisit(jsonArray);
                            if(msg.equals("Inserted")){
                                msg="Master Visit:"+jsonArray.length();
                                msg=msg+"\n\n\nConfiguration has been Finished...";
                            }else{
                                msg="Master Visit not Inserted:"+msg+",json:"+jsonArray.length();
                                msg=msg+"\n\n\nConfiguration has been Finished...";
                            }

                        } else {
                            msg="Master Visit not Downloaded:";
                        }
                    } else {
                        msg="Response not found"+jsonArray.length();
                    }
                    //return sb.toString();
                    in.close();

                } else {
                    msg="Response not found"+jsonArray.length();
                    //return new String("false : " + responseCode);
                }
            } catch (Exception ex) {
                msg = "Exception" + ex.getStackTrace();
            }

            return  null;
        }

    }

//==============================Visits================================



}
