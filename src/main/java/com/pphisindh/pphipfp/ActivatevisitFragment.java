package com.pphisindh.pphipfp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivatevisitFragment extends Fragment implements View.OnClickListener {

    Button btnLogin;
    Button btnCancel;
    TextView tvMsg;
    EditText etDate;
    String msg = "";
    SQLiteController controller;


    public ActivatevisitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activatevisit, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try{

            controller = new SQLiteController(getActivity(), ApplicationVariables.dbName, null, 1);
            etDate = (EditText) getActivity().findViewById(R.id.etDate);
            btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
            btnCancel = (Button) getActivity().findViewById(R.id.btnCancel);
            tvMsg = (TextView) getActivity().findViewById(R.id.tvMsg);

            etDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        DateDialog3 dialog = new DateDialog3();
                        dialog.etText(etDate);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        dialog.show(getActivity().getFragmentManager(), "datePicker");


                    }catch (Exception ex){

                    }



                }
            } );

            btnLogin.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

        }catch (Exception ex){

        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnLogin: {
                    if (etDate.getText() != null && etDate.getText() + "" != "") {


                        tvMsg.setText("Please wait....!");
                        String msg=controller.activatevisits(controller.getvisit(""+etDate.getText()));
                        tvMsg.setText(""+msg);


                    } else {
                        msg = "Please Insert date....!";
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
}
