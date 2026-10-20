package com.pphisindh.pphipfp;

import android.app.DownloadManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.FragmentTransaction;

public class RegisterVisitActivity extends AppCompatActivity {

    private DownloadManager mgr;
    private Uri downloadUri;
    private LocationManager locationManager;

    GPSTracker2 gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_visit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try{
            gps = new GPSTracker2(this);
            if(gps.canGetLocation()){

                LocationsCls.longitude=gps.getLongitude();
                LocationsCls.latitude=gps.getLatitude();

                // \n is for new line
                //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + LocationsCls.latitude + "\nLong: " + LocationsCls.latitude, Toast.LENGTH_LONG).show();
            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
        }catch (Exception ex){

        }
        RegisterVisitFragment fragment=new RegisterVisitFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main,fragment);
        fragmentTransaction.commit();
    }

    /**
     * Created by Administrator on 12/25/2017.
     */


}
