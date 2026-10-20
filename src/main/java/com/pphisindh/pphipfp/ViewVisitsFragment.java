package com.pphisindh.pphipfp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewVisitsFragment extends Fragment {


    TextView tvCountMasterVisits,tvCountMasterVisitsUnupload;
    TextView tvCountAns;
    TextView tvViewAttendance;

    SQLiteController controller;
    public ViewVisitsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_visits, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{
             controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
             tvCountMasterVisits=(TextView)getActivity().findViewById(R.id.tvCountMasterVisits);
            tvCountMasterVisitsUnupload=(TextView)getActivity().findViewById(R.id.tvCountMasterVisitsUnupload);
             tvCountAns=(TextView)getActivity().findViewById(R.id.tvCountAns);
             tvViewAttendance=(TextView)getActivity().findViewById(R.id.tvViewAttendance);

             tvCountMasterVisits.setText("Total Visit(s)\n"+controller.CountMasterVisitsUnupload("1"));
            tvCountMasterVisitsUnupload.setText("Pending Visit(s)\n"+controller.CountMasterVisitsUnupload("0"));
             tvCountAns.setText("Visit Details\n"+controller.countDetailVisits());
            tvViewAttendance.setText("Attendance Details\n"+controller.countAttendance());


        }catch (Exception ex){

        }
    }
}
