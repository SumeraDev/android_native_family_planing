package com.pphisindh.pphipfp;

import com.pphisindh.pphipfp.Services.DownloadEmployees;

/**
 * Created by Administrator on 8/9/2017.
 */

public class Download {

    public String donwloadEmployees(){
        String msg="Data has been Downloaded...!";
        try{
            new DownloadEmployees().execute("");

        }catch (Exception ex){

        }
        return msg;
    }




}
