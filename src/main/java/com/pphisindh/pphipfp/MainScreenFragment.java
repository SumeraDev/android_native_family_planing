package com.pphisindh.pphipfp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreenFragment extends Fragment implements  View.OnClickListener {

    Button btnDownload;
    Button btnUpload;

    Button btnRegister;
    Button btnViewVisit;
    Button btnActivatevisit;
    Button btncompletesetup;
    TextView tvQuarter;
    TextView tvTotal,tvTotalUploaded,tvTotalPending,tvDetailed;
    SQLiteController controller;
    public MainScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{

            controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
            tvQuarter=(TextView) getActivity().findViewById(R.id.tvQuarter);
            tvQuarter.setText("Active Quarter is "+controller.getQuarter());

            tvTotal=(TextView) getActivity().findViewById(R.id.tvTotal);
            tvTotalUploaded=(TextView) getActivity().findViewById(R.id.tvTotalUploaded);
            tvTotalPending=(TextView) getActivity().findViewById(R.id.tvTotalPending);
           // tvDetailed=(TextView) getActivity().findViewById(R.id.tvDetailed);

            tvTotal.setText("Total Visit(s)\n"+controller.countMasterVisits());
            tvTotalUploaded.setText("Uploaded Visit(s)\n"+controller.CountMasterVisitsUnupload("0"));
            tvTotalPending.setText("Pending Visit(s)\n"+controller.CountMasterVisitsUnupload("1"));
     //       tvDetailed.setText("Detailed Visit(s)\n"+controller.countDetailVisits());
//            tvViewAttendance.setText("Attendance Details\n"+controller.countAttendance());

            btnDownload=(Button)getActivity().findViewById(R.id.btnDownload);
            btnUpload=(Button)getActivity().findViewById(R.id.btnUpload);
            btnRegister=(Button)getActivity().findViewById(R.id.btnRegister);
            btnViewVisit=(Button)getActivity().findViewById(R.id.btnViewVisit);
            btnActivatevisit=(Button)getActivity().findViewById(R.id.btnActivatevisit);
            btncompletesetup=(Button)getActivity().findViewById(R.id.btncompletesetup);


            btnDownload.setOnClickListener(this);
            btnUpload.setOnClickListener(this);
            btnRegister.setOnClickListener(this);
            btnViewVisit.setOnClickListener(this);
            btnActivatevisit.setOnClickListener(this);
            btncompletesetup.setOnClickListener(this);


        }catch (Exception ex){

        }
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnDownload:{
                    Intent i = new Intent(this.getActivity(), DownloadDataActivity.class);
                    startActivity(i);
                    break;
                }case R.id.btnUpload:{
                    Intent i = new Intent(this.getActivity(), UploadDataActivity.class);
                    startActivity(i);
                    break;
                }case R.id.btnRegister:{
                    Intent i = new Intent(this.getActivity(), RegisterVisitActivity.class);
                    startActivity(i);
                    break;
                }case R.id.btnViewVisit:{
                    Intent i = new Intent(this.getActivity(), ViewVisitsActivity.class);
                    startActivity(i);
                    break;
                }case R.id.btnActivatevisit:{
                    Intent i = new Intent(this.getActivity(), ActivatevisitActivity.class);
                    startActivity(i);
                    break;
                }case R.id.btncompletesetup:{
                    Intent i = new Intent(this.getActivity(), VisithfActivity.class);
                    startActivity(i);
                    break;
                }

            }
        }catch (Exception ex){

        }
    }
}
