package com.pphisindh.pphipfp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;

    Logindb logindb;
    Button btnLogin;
    Button btnCancel;
    Loginsvc loginsvc;
    String username = "";
    String pwd = "";
    String msg = "";
    TextView tvMsg;
    TelephonyManager telephonyManager;
    EditText etUsername;
    TextView eteimi;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            logindb = new Logindb(getActivity());
            //loginsvc=new Loginsvc();
            //controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
            etUsername = (EditText) getActivity().findViewById(R.id.etuname);
            eteimi = (TextView) getActivity().findViewById(R.id.eteimi);
            telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
//            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            eteimi.setText("IMEI : " + telephonyManager.getDeviceId() + "");
//            pwd ="123";//
            //pwd=telephonyManager.getDeviceId() + "";//"295687143356027";//"053326644959398";"123";//


//
            telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                },1);

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
////                eteimi.setText("IMEI : " + telephonyManager.getDeviceId() + "");
////                pwd=telephonyManager.getDeviceId();
//
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
//                    eteimi.setText("IMEI : 72C3661BA0D1" );
//                    pwd= "72C3661BA0D1";
                    eteimi.setText("ID : " +getIMEIDeviceId(getContext()));
                    pwd=""+getIMEIDeviceId(getContext());

                }else{
                    eteimi.setText("ID : " + getIMEIDeviceId(getContext()) + "");
                    pwd = getIMEIDeviceId(getContext());
                }


            }else{
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
////                eteimi.setText("IMEI : " + telephonyManager.getDeviceId() + "");
////                pwd=telephonyManager.getDeviceId();
//
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
//                    eteimi.setText("IMEI : " + Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID) + "");
//                    pwd= Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    eteimi.setText("ID : " +getIMEIDeviceId(getContext()));
                    pwd=""+getIMEIDeviceId(getContext());

//                    eteimi.setText("IMEI : 72C3661BA0D1");
                    //pwd="18E777D31C43";

                    //72C3661BA0D1
                }else{
                    eteimi.setText("ID : " + getIMEIDeviceId(getContext()) + "");
                    pwd = getIMEIDeviceId(getContext());

                }

            }
            if(pwd==null && pwd.length()==0){
                telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
//                    eteimi.setText("IMEI : 72C3661BA0D1" );
//                    pwd= "72C3661BA0D1";
                    eteimi.setText("ID : " +getIMEIDeviceId(getContext()));
                    pwd=""+getIMEIDeviceId(getContext());

                }else{
                    eteimi.setText("ID : " + getIMEIDeviceId(getContext()) + "");
                    pwd = getIMEIDeviceId(getContext());
                }

            }





            btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
            btnCancel = (Button) getActivity().findViewById(R.id.btnCancel);
            tvMsg = (TextView) getActivity().findViewById(R.id.tvMsg);
            //telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

            btnLogin.setOnClickListener(this);
            btnCancel.setOnClickListener(this);


        } catch (Exception ex) {

        }
    }

    public static String getIMEIDeviceId(Context context) {

        String deviceId;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
        {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return "";
                }
            }
            assert mTelephony != null;
            if (mTelephony.getDeviceId() != null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    deviceId = mTelephony.getImei();
                }else {
                    deviceId = mTelephony.getDeviceId();
                }
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        //Log.d("deviceId", deviceId);
        return deviceId;
    }

    public static String getDeviceId(Context context) {

        String id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return id;
    }
    public  String getMacAddr() {
        try {



            //WifiConfiguration.getRandomizedMacAddress();
            //telephonyManager.geta
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            String stringmac="";
            for (NetworkInterface nif : all) {

                if (nif.getName().equalsIgnoreCase("wlan0")){
                    for(int i=0;    i<nif.getHardwareAddress().length   ; i++){
                        String str=Integer.toHexString(nif.getHardwareAddress()[i]&0xff);
                        if(str.length()==1){
                            stringmac=stringmac+"0"+str.toUpperCase();//+":";
                        }else{
                            stringmac=stringmac+""+str.toUpperCase();//+":";
                        }

                    }
                    break;
                }

            }
            return stringmac;//.substring(0,stringmac.length()-1);
        } catch (Exception ex) {
            //handle exception
            Toast.makeText(getActivity(),"Error : "+ex.getMessage(),Toast.LENGTH_LONG).show();
            //alert("Error in mac:"+ex.getMessage());
        }
        return "";
    }


//    public  String getMacAddr() {
//        try {
//
//
//
//            //WifiConfiguration.getRandomizedMacAddress();
//            //telephonyManager.geta
//            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
//            String stringmac="";
//            for (NetworkInterface nif : all) {
//
//                if (nif.getName().equalsIgnoreCase("wlan0")){
//                    for(int i=0;    i<nif.getHardwareAddress().length   ; i++){
//                        String str=Integer.toHexString(nif.getHardwareAddress()[i]&0xff);
//                        if(str.length()==1){
//                            stringmac=stringmac+"0"+str.toUpperCase();//+":";
//                        }else{
//                            stringmac=stringmac+""+str.toUpperCase();//+":";
//                        }
//
//                    }
//                    break;
//                }
//
//            }
//            return stringmac;//.substring(0,stringmac.length()-1);
//        } catch (Exception ex) {
//            //handle exception
//            Toast.makeText(getActivity(),"Error in mac:"+ex.getMessage(),Toast.LENGTH_LONG).show();
//            //alert("Error in mac:"+ex.getMessage());
//        }
//        return "";
//    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnLogin: {
                    if (etUsername.getText() != null && etUsername.getText() + "" != "" && eteimi.getText() != null && eteimi.getText() + "" != "") {
    //Log.d("aijaztest","aijaztest"+etUsername.getText() + " - "+eteimi.getText());
                        tvMsg.setText("Please wait....!");
                        new Loginsvc().execute();

                    } else {
                        msg = "Invalid Cnic!";
                        tvMsg.setText(msg);
                    }
                    break;
                }
                case R.id.btnCancel: {
                    getActivity().finish();
                    break;
                }
            }
        } catch (Exception ex) {
            tvMsg.setText("Error in Login Fragment" + ex.getMessage());
        }


    }

    class Loginsvc extends AsyncTask<String, String, String> {

        JSONArray jsonArray = null;
        //http://jobs.pphisindh.org
        String pathName = ApplicationVariables.urlPublic;
        //String pathName = "https://tms.pphisindh.org/mom/";
        String response;

        @Override
        protected void onPostExecute(String str) {
            try {
                if (msg.equals("Login Inserted")) {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                } else {
                    tvMsg.setText(msg+","+jsonArray.length());
                }
            } catch (Exception ex) {

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                username = etUsername.getText() + "";
                //pwd=eteimi.getText()+"";
                tvMsg.setText("Please wait....."+pwd);
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

                //String path = "http://203.170.79.130/mom/Loginsvc.php";
                //String path = "https://tms.pphisindah.org/mom/Loginsvc.php";
                String path = ApplicationVariables.urlPublic+"Loginsvc.php";
                //String path = "https://tms.pphisindh.org/services/test/mom/Loginsvc.php?username=4210133329491&pwd=862223032193281";
               // String path = "http://tms.pphisindh.org/services/mom/Loginsvc.php?username=1&pwd=c775052072b13b42";
                Log.e("paramss", path.toString());

                msg = "Before url";
                URL url = new URL(path);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("username", username + "");
                postDataParams.put("pwd", pwd + "");

                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(25000 /* milliseconds */);
                //conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                //msg="before Write";
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                //msg="Before response";
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    response = in.readLine();
                    if (response != null && response != "[]") {

                        jsonArray = new JSONArray(response);

                        Log.e("params", jsonArray.toString());
                        if (jsonArray != null) {
                            msg = logindb.insertLogin(jsonArray);
                        } else {
                            msg = "Login not Verified...!" + jsonArray.length();
                        }
                    } else {
                        msg = "Login not verified:" + jsonArray.length();
                    }
                    //return sb.toString();
                    in.close();

                } else {
                    msg = "Login not verified:" + jsonArray.length();
                }
            } catch (Exception ex) {
                msg = "Error in Login:" + ex.getStackTrace() + "," + ex.getMessage() + "," + msg;
            }


            return null;
        }
    }
}
