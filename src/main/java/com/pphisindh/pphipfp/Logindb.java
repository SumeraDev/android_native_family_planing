package com.pphisindh.pphipfp;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by Administrator on 8/10/2017.
 */

public class Logindb {
    Activity activity;

    SQLiteController controller;
    public Logindb(Activity activity){
        try{
            this.activity=activity;
            controller = new SQLiteController(activity, ApplicationVariables.dbName, null, 1);
        }catch (Exception ex){

        }
    }
    public int isLogin(){
        int chkLogin=0;
        try{
            chkLogin=controller.chkLogin();
        }catch (Exception ex){

        }
        return chkLogin;
    }
    public String insertLogin(JSONArray jsonArray){
        String msg="Invalid username or Password....!";
        try{
            for(int index=0;index<jsonArray.length(); index++){
              JSONObject jsonObject=jsonArray.getJSONObject(index);
              msg=controller.InsertLogin(jsonObject.getInt("empid"),jsonObject.getString("username"),jsonObject.getString("password"),"2222");
            }
        }catch (Exception ex){
            msg="Error in Login:"+ex.getMessage();
        }
        return msg;
    }

    public String  veryfyPincode(String pincode){
        String msg="not Verified";
        try{
            if(controller.verifyPincode(pincode)>0){
                msg="isVerified";
            }
        }catch (Exception ex){

        }
        return msg;
    }

}
