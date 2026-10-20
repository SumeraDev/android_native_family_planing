package com.pphisindh.pphipfp.Services;

import android.os.AsyncTask;
import android.util.Log;

import com.pphisindh.pphipfp.ApplicationVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 8/9/2017.
 */

public class DownloadHealthFacilities extends AsyncTask<String, String, String> {
    int empId;
    String msg;
    JSONArray jsonArray =null;
    String pathName= ApplicationVariables.urlPublic;
    //String pathName="https://tms.pphisindh.org/mom/";
    String response;
    public void setEmpId(int empId){
        try{
            this.empId=empId;

        }catch (Exception ex){

        }
    }
    @Override
    protected void onPostExecute(String str) {
        try {

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
            String path = pathName+"DownloadHealthFacilities.php";
            URL url = new URL(path);
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("empId", this.empId + "");
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
                        //msg = controller.SyncdownChildVaccinations(jsonArray);
                        msg="Employees:"+jsonArray.length();
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

    public String getMsg(){
        return msg;
    }
}